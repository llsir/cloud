package com.boss.auth.service.imp;

import com.alibaba.fastjson.JSON;
import com.boss.auth.entity.dto.AuthToken;
import com.boss.auth.entity.dto.UserTokenStore;
import com.boss.auth.exception.ExceptionCast;
import com.boss.auth.service.AuthService;
import com.boss.auth.entity.vo.LoginRequestVO;
import com.boss.framework.model.response.AuthCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: lpb
 * @create: 2020-07-28 16:49
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Value("${auth.clientId}")
    String clientId;
    @Value("${auth.clientSecret}")
    String clientSecret;
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    @Override
    public AuthToken login(LoginRequestVO loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        AuthToken authToken = applyToken(username, password);
        String access_token = authToken.getAccess_token();
        String content = JSON.toJSONString(authToken);
        boolean saveTokenResult = saveToken(access_token, content, tokenValiditySeconds);
        if(!saveTokenResult){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }
        return authToken;
    }

    @Override
    public UserTokenStore getUserToken(String token) {
        String userToken = "user_token:" + token;
        String userTokenString = stringRedisTemplate.opsForValue().get(userToken);
        if(userToken!=null){
            UserTokenStore userTokenStore = null;
            try {
                userTokenStore = JSON.parseObject(userTokenString, UserTokenStore.class);
            } catch (Exception e) {
                log.error("getUserToken from redis and execute JSON.parseObject error {}",e.getMessage());
                e.printStackTrace();
            }
            return userTokenStore;
        }
        return null;
    }

    /**
     * 用户退出时，删除用户身份信息
     * @param token
     * @return
     */
    @Override
    public boolean delToken(String token) {
        String name = "user_token:" + token;
        stringRedisTemplate.delete(name);
        return true;
    }

    //认证方法
    public AuthToken applyToken(String username,String password){
        //得到服务的url
        String url = "http://service-auth/oauth/token";
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        String basic = getBasic(clientId, clientSecret);
        headers.add("Authorization",basic);
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(body,headers);
        Map map = null;
        try {
            ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
                @Override
                public void handleError(ClientHttpResponse response) throws IOException {
                    // 设置 当响应400和401时照常响应数据，不要报错
                    if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401 ) {
                        super.handleError(response);
                    }
                }
            });
            //http请求spring security的申请令牌接口
            ResponseEntity<Map> mapResponseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            map = mapResponseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            log.error("request oauth_token_password error: {}",e.getMessage());
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }

        if(map == null ||
                map.get("access_token") == null ||
                map.get("refresh_token") == null ||
                map.get("jti") == null){//jti是jwt令牌的唯一标识作为用户身份令牌
            //获取spring security返回的错误信息
            String error_description = (String) map.get("error_description");
            if(StringUtils.isNotEmpty(error_description)){
                if(error_description.equals("坏的凭证")){
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }else if(error_description.indexOf("UserDetailsService returned null")>=0){
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                }else if(error_description.equals("用户帐号已被锁定")){
                    ExceptionCast.cast(AuthCode.AUTH_USER_LOCKED);
                }else if(error_description.equals("用户帐号已过期")){
                    ExceptionCast.cast(AuthCode.AUTH_USER_CANCEL);
                }
            }
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        AuthToken authToken = new AuthToken();
        //访问令牌(jwt)
        String jwt_token = (String) map.get("access_token");
        //刷新令牌(jwt)
        String refresh_token = (String) map.get("refresh_token");
        //jti，作为用户的身份标识
        String access_token = (String) map.get("jti");
        authToken.setJwt_token(jwt_token);
        authToken.setAccess_token(access_token);
        authToken.setRefresh_token(refresh_token);
        return authToken;
    }

    //获取Base64编码
    private String getBasic(String cliendId,String clientSecret){
        String format =  cliendId+":"+clientSecret;
        byte[] encode = new byte[0];
        try {
            encode = Base64.encode(format.getBytes("utf-8"));
            //生成http basic auth串
            return "Basic "+new String(encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //存储令牌到redis
    private boolean saveToken(String access_token,String content,long ttl){
        //令牌名称
        String name = "user_token:" + access_token;
        //保存到令牌到redis
        stringRedisTemplate.boundValueOps(name).set(content,ttl, TimeUnit.SECONDS);
        //获取过期时间
        Long expire = stringRedisTemplate.getExpire(name);
        return expire>0;
    }
}
