package com.boss.ucenter.entity.vo;

import lombok.Data;

/**
 * @author: lpb
 * @create: 2020-07-29 10:07
 */
@Data
public class AddPermissionVO {

    private String code;

    private Long pId;

    private String menuName;

    private String isMenu;

    private Integer level;

    private String sort;

}
