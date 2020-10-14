package com.boss.auth.service;

import com.boss.auth.entity.dto.AuthToken;
import com.boss.auth.entity.dto.UserTokenStore;
import com.boss.auth.entity.vo.LoginRequestVO;

/**
 * @author: lpb
 * @create: 2020-07-28 16:49
 */
public interface AuthService {

    AuthToken login(LoginRequestVO loginRequest);

    /**
     * 从redis中获取用户的jwt身份令牌
     * @param token
     * @return
     */
    UserTokenStore getUserToken(String token);

    /**
     * 用户退出时，删除用户身份信息
     * @param token
     */
    boolean delToken(String token);
}
