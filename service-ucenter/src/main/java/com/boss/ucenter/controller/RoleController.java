package com.boss.ucenter.controller;

import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.entity.vo.EditRolePermissionRequest;
import com.boss.ucenter.entity.vo.RoleInfo;
import com.boss.ucenter.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 18:56
 */
@RestController
@RequestMapping("/role")
@Api(value = "角色管理中心")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/list")
    @ApiOperation(value = "查询全部角色及其权限信息")
    public QueryResponseResult<List> list(){
        return roleService.list();
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加角色")
    public ResponseResult add(@RequestBody RoleInfo roleInfo){
        return roleService.add(roleInfo);
    }

    @PutMapping("/edit")
    @ApiOperation(value = "修改角色信息")
    public ResponseResult edit(@RequestBody RoleInfo roleInfo){
        return roleService.edit(roleInfo);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除角色信息")
    public ResponseResult delete(@RequestParam("roleId")Long roleId){
        return roleService.delete(roleId);
    }

    @PostMapping("/addPermissions")
    @ApiOperation(value = "给角色添加部分权限")
    public ResponseResult addPermissions(@RequestBody EditRolePermissionRequest editRolePermissionRequest){
        return roleService.addPermissions(editRolePermissionRequest);
    }

    @DeleteMapping("/removePermissions")
    @ApiOperation(value = "删除角色部分权限")
    public ResponseResult removePermissions(@RequestBody EditRolePermissionRequest editRolePermissionRequest){
        return roleService.removePermissions(editRolePermissionRequest);
    }


}
