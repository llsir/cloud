package com.boss.ucenter.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: lpb
 * @create: 2020-07-21 11:06
 */
@Data
@NoArgsConstructor
@TableName("permission")
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    @TableField("p_id")
    private Long pId;

    @TableField("menu_name")
    private String menuName;

    @TableField("is_menu")
    private String isMenu;

    private Integer level;

    private Integer sort;

    private String status;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

}
