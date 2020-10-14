package com.boss.ucenter.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: lpb
 * @create: 2020-07-21 11:06
 */
@Data
@NoArgsConstructor
@TableName("role_permission")
public class RolePermission {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("role_id")
    private Long roleId;

    @TableField("permission_id")
    private Long permissionId;

    @TableField("createTime")
    private Date create_time;
}
