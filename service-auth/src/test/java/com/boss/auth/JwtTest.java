package com.boss.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lpb
 * @create: 2020-07-28 10:18
 */
public class JwtTest {

    @Test
    public void testCreateJwt(){
        String path = "keystore.jks";//证书库
        //密钥库的密码
        String keyStore_password = "123456";
        //密钥的别名
        String alias = "bosskey";
        //密钥的密码
        String password = "mypass";
        ClassPathResource classPathResource = new ClassPathResource(path);
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, keyStore_password.toCharArray());
        //密钥对(密钥和公钥)
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, password.toCharArray());
        //得到密钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        //定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "123");
        tokenMap.put("name", "lsir");
        tokenMap.put("roles", "r01,r02");
        tokenMap.put("ext", "1");
        //生成jwt令牌
        Jwt encode = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivateKey));
        String encoded = encode.getEncoded();
    }

    @Test
    public void testVerify(){
        //jwt令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJzdXBlciIsInNjb3BlIjpbImFwcCJdLCJuYW1lIjoi6LaF57qn566h55CG5ZGYIiwidXR5cGUiOiIxMDEwMDUiLCJpZCI6NDYsImV4cCI6MTU5NjAxNDYxNCwiYXV0aG9yaXRpZXMiOlsieGNfc3lzbWFuYWdlcl9kb2MiLCJ4Y19zeXNtYW5hZ2VyX3JvbGUiLCJ4Y19zeXNtYW5hZ2VyX3VzZXJfdmlldyIsInhjX3N5c21hbmFnZXJfcm9sZV9lZGl0IiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9wbGFuIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZSIsInhjX3RlYWNobWFuYWdlciIsInhjX3N5c21hbmFnZXJfdXNlcl9hZGQiLCJ4Y19zeXNtYW5hZ2VyX3VzZXJfZGVsZXRlIiwieGNfc3lzbWFuYWdlcl9yb2xlX2FkZCIsInhjX3N5c21hbmFnZXJfdXNlciIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfbGlzdCIsInhjX3N5c21hbmFnZXIiLCJ4Y19zeXNtYW5hZ2VyX3VzZXJfZWRpdCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfbWFya2V0IiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9hZGQiXSwianRpIjoiN2M5YTUwOTEtNWY3MC00NmZjLTljOTEtZjc3NDc5NGI1NzAxIiwiY2xpZW50X2lkIjoiQm9zc1dlYkFwcCJ9.V0HW6Ha_rVPGZZ-5wYHikWzJgHxwuRRdckXTSXVWfCvu8hIYoUow6n_kqcGnyoPDi6HaMAsmocCTBN6rY8ZsP2r68rE5DkxvBX4SzwUHQWl5IXkmPq2Pr3DMAeB97mRDWY3L7Z5ph3DaKVRP4f_09wLQD7xIp3q4yDdSv8R4PcXVUVS4ekOVcsNF8R4iciKihj7-GNes7gZg69io_EA6_njd1Qxd8oq9h8lTFWIYmzK0wc9uZgGPaFaJqwK8451w51jYBPa5mv9UqGkSFdetkm-XGDJFdNrtusCmIbByW2d_nVpqWI3e7rrPBK1VRfo0PE5E5IbMFU_8uobnvwtaBA";
        //公钥
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlCnn6grfCxyuf0Hu8AW4ZLoohtqF063jlNxdfJi9jL3VJUJbVIH4FTkhu4SwSTPaL9g8ha+pMrbq08+HDV5gdthgV7F/e5BOSWYE47kHyK8FqoUWlTsfy8U4VD2JQB7izSe9OsZ19MMxB1wpW+CIR81UwgUkA2H3ZdT050JoJjkn/9D1mnK3xko/6/9lqhDDix9uN4aRqjFdkIRlo8mCY9CC3OmacJ7Es1CEsKFYVwKoIWmzJrFYVumEXRtGsiDxQMXYUcNijwhquvepvQ90txF41/VZUN7ahzWiykvo0i7IvcE76rn5JAdBUkjankBW1HWv7PAqIO8ecJqTnLRu8wIDAQAB-----END PUBLIC KEY-----";
        RsaVerifier rsaVerifier = new RsaVerifier(publicKey);
        Jwt jwt = JwtHelper.decodeAndVerify(token, rsaVerifier);
        String claims = jwt.getClaims();
        String encoded = jwt.getEncoded();
    }

}
