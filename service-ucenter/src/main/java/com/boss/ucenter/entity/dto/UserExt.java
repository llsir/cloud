package com.boss.ucenter.entity.dto;

import com.boss.ucenter.entity.po.Permission;
import com.boss.ucenter.entity.po.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-21 11:14
 */
@Data
@NoArgsConstructor
@ToString
public class UserExt extends User {

    private List<Permission> permissions;

}
