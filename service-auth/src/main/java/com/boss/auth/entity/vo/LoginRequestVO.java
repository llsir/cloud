package com.boss.auth.entity.vo;

import lombok.Data;

/**
 * @author: lpb
 * @create: 2020-07-28 16:48
 */
@Data
public class LoginRequestVO {

    String username;

    String password;

    String verifycode;

}
