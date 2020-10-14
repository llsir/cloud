package com.boss.ucenter.service;

import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.entity.vo.EditRolePermissionRequest;
import com.boss.ucenter.entity.vo.RoleInfo;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 19:01
 */
public interface RoleService {

    /**
     * 查询全部角色及其权限信息
     * @return
     */
    QueryResponseResult<List> list();

    /**
     * 修改角色信息
     * @param roleInfo 角色信息
     * @return
     */
    ResponseResult edit(RoleInfo roleInfo);

    /**
     * 为指定角色添加权限
     * @param addPermissionRequest 角色id，相关权限id
     * @return
     */
    ResponseResult addPermissions(EditRolePermissionRequest addPermissionRequest);

    /**
     * 移除指定角色某些权限
     * @param editRolePermissionRequest 角色id，相关权限id
     * @return
     */
    ResponseResult removePermissions(EditRolePermissionRequest editRolePermissionRequest);

    /**
     * 添加角色
     * @param roleInfo 角色信息
     * @return
     */
    ResponseResult add(RoleInfo roleInfo);

    /**
     * 删除指定角色
     * @param roleId 角色id
     * @return
     */
    ResponseResult delete(Long roleId);
}
