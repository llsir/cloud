package com.boss.auth.controller;

import com.boss.auth.entity.dto.UserTokenStore;
import com.boss.auth.utils.CookieUtil;
import com.boss.auth.entity.dto.AuthToken;
import com.boss.auth.service.AuthService;
import com.boss.auth.entity.vo.LoginRequestVO;
import com.boss.framework.model.response.CommonCode;
import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: lpb
 * @create: 2020-07-28 15:58
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    AuthService authService;

    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    @PostMapping("/login")
    public QueryResponseResult login(LoginRequestVO loginRequest){
        AuthToken authToken = authService.login(loginRequest);
        String access_token = authToken.getAccess_token();
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", access_token, cookieMaxAge, false);
        return new QueryResponseResult<String>(CommonCode.SUCCESS,access_token);
    }

    @GetMapping("/userjwt")
    public QueryResponseResult userjwt() {
        //获取cookied中的token
        Map<String, String> cookie = CookieUtil.readCookie(request, "uid");
        String token = cookie.get("uid");
        //根据令牌从redis查询jwt
        UserTokenStore userTokenStore = authService.getUserToken(token);
        if(userTokenStore == null){
            return new QueryResponseResult<String>(CommonCode.FAIL,null);
        }
        return new QueryResponseResult<String>(CommonCode.SUCCESS,userTokenStore.getJwt_token());
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        Map<String, String> cookie = CookieUtil.readCookie(request, "uid");
        String token = cookie.get("uid");
        authService.delToken(token);
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, 0, false);
        return ResponseResult.SUCCESS();
    }
}
