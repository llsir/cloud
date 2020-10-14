package com.boss.ucenter.entity.vo;

import lombok.Data;

/**
 * @author: lpb
 * @create: 2020-07-27 19:42
 */
@Data
public class RoleInfo {

    private Long id;

    private String roleName;

    private String roleCode;

    private String description;

    private String status;
}
