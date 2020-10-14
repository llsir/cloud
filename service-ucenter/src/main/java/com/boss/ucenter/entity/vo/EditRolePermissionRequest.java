package com.boss.ucenter.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 19:46
 */
@Data
public class EditRolePermissionRequest {

    private Long roleId;

    private List<Long> permissionIds;
}
