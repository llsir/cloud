package com.boss.ucenter.entity.dto;

import com.boss.ucenter.entity.po.Permission;
import com.boss.ucenter.entity.po.Role;
import lombok.Data;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 18:59
 */
@Data
public class RoleExt extends Role {

    private List<Permission> permissions;

}
