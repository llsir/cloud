package com.boss.ucenter.service.imp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boss.framework.model.response.CommonCode;
import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.dao.imp.RoleMapper;
import com.boss.ucenter.dao.imp.RolePermissionMapper;
import com.boss.ucenter.entity.dto.RoleExt;
import com.boss.ucenter.entity.po.Role;
import com.boss.ucenter.entity.po.RolePermission;
import com.boss.ucenter.entity.po.User;
import com.boss.ucenter.entity.vo.EditRolePermissionRequest;
import com.boss.ucenter.entity.vo.RoleInfo;
import com.boss.ucenter.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: lpb
 * @create: 2020-07-27 19:01
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    /**
     * 查询全部角色及其权限信息
     * @return
     */
    @Override
    public QueryResponseResult<List> list() {
        List<RoleExt> allRoles = roleMapper.getAllRoles();
        return new QueryResponseResult<>(CommonCode.SUCCESS,allRoles);
    }

    /**
     * 修改角色信息
     * @param roleInfo 角色信息
     * @return
     */
    @Override
    public ResponseResult edit(RoleInfo roleInfo) {
        Role role = roleMapper.selectById(roleInfo.getId());
        if(role == null){
            return new ResponseResult(CommonCode.ROLE_NOTEXISTS);
        }
        BeanUtils.copyProperties(roleInfo,role);
        role.setUpdateTime(new Date());
        roleMapper.updateById(role);
        return ResponseResult.SUCCESS();
    }

    /**
     * 为指定角色添加权限
     * @param editRolePermissionRequest 角色id，相关权限id
     * @return
     */
    @Override
    @Transactional
    public ResponseResult addPermissions(EditRolePermissionRequest editRolePermissionRequest) {
        Long roleId = editRolePermissionRequest.getRoleId();
        Role role = roleMapper.selectById(roleId);
        if(role == null){
            return new ResponseResult(CommonCode.ROLE_NOTEXISTS);
        }
        RolePermission rolePermission = new RolePermission();
        List<Long> permissionIds = editRolePermissionRequest.getPermissionIds();
        rolePermission.setRoleId(roleId);
        for(long pid : permissionIds){
            rolePermission.setPermissionId(pid);
            rolePermission.setCreate_time(new Date());
            rolePermissionMapper.insert(rolePermission);
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 移除指定角色某些权限
     * @param editRolePermissionRequest 角色id，相关权限id
     * @return
     */
    @Override
    @Transactional
    public ResponseResult removePermissions(EditRolePermissionRequest editRolePermissionRequest) {
        Long roleId = editRolePermissionRequest.getRoleId();
        Role role = roleMapper.selectById(roleId);
        if(role == null){
            return new ResponseResult(CommonCode.ROLE_NOTEXISTS);
        }
        List<Long> permissionIds = editRolePermissionRequest.getPermissionIds();
        rolePermissionMapper.deleteByRoleIdAndPermissionIds(roleId,permissionIds);
        return ResponseResult.SUCCESS();
    }

    /**
     * 添加角色
     * @param roleInfo 角色信息
     * @return
     */
    @Override
    public ResponseResult add(RoleInfo roleInfo) {
        Role role = new Role();
        BeanUtils.copyProperties(roleInfo,role);
        role.setCreateTime(new Date());
        roleMapper.insert(role);
        return ResponseResult.SUCCESS();
    }

    /**
     * 删除指定角色
     * @param roleId 角色id
     * @return
     */
    @Override
    @Transactional
    public ResponseResult delete(Long roleId) {
        Role role = roleMapper.selectById(roleId);
        if(role == null){
            return new ResponseResult(CommonCode.ROLE_NOTEXISTS);
        }
        role.setStatus("4");
        role.setUpdateTime(new Date());
        roleMapper.updateById(role);
        return ResponseResult.SUCCESS();
    }
}
