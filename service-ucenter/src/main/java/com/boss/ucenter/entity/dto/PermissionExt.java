package com.boss.ucenter.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 21:01
 */
@Data
public class PermissionExt {

    private Long id;

    private String code;

    private String menuName;

    private String isMenu;

    private Integer level;

    private String status;

    private List<PermissionExt> children;

}
