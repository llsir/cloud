package com.boss.ucenter.controller;

import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.entity.vo.AddPermissionVO;
import com.boss.ucenter.entity.vo.EditPermissionVO;
import com.boss.ucenter.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 20:54
 */
@RestController
@RequestMapping("/permission")
@Api(value = "权限管理中心")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/list")
    @ApiOperation(value = "查询全部权限信息")
    public QueryResponseResult<List> list(){
        return permissionService.list();
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加权限信息")
    public ResponseResult add(@RequestBody AddPermissionVO addPermission){
        return permissionService.add(addPermission);
    }

    @PutMapping("/edit")
    @ApiOperation(value = "修改权限信息")
    public ResponseResult edit(@RequestBody EditPermissionVO editPermission){
        return permissionService.edit(editPermission);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "移除权限信息")
    public ResponseResult remove(@RequestParam("pid") Long pid){
        return permissionService.remove(pid);
    }
}
