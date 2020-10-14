package com.boss.ucenter.controller;

import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.QueryResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.entity.dto.UserExt;
import com.boss.ucenter.entity.po.User;
import com.boss.ucenter.entity.vo.QueryUserRequestVO;
import com.boss.ucenter.entity.vo.UserInfo;
import com.boss.ucenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-21 11:43
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户中心",description = "用户中心管理")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserExt")
    @ApiOperation(value = "获取用户权限相关信息")
    public UserExt getUserExt(@RequestParam(value = "username",required = true) String userName){
        return userService.getUserExt(userName);
    }

    @GetMapping("/list/{page}/{size}")
    @ApiOperation(value = "分页查询用户")
    public QueryResponseResult<QueryResult> list(@PathVariable("page") Integer page, @PathVariable("size")Integer size, QueryUserRequestVO queryUserRequest){
        return userService.list(page,size,queryUserRequest);
    }

    @GetMapping("/one/{uid}")
    @ApiOperation(value = "查询指定ID用户")
    public QueryResponseResult<User> getOne(@PathVariable("uid") Long uid){
        return userService.getOne(uid);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加用户")
    public ResponseResult addUser(@RequestBody UserInfo userInfo){
        return userService.add(userInfo);
    }

    @PutMapping("/edit")
    @ApiOperation(value = "修改用户信息")
    public ResponseResult editUser(@RequestBody UserInfo userInfo){
        return userService.edit(userInfo);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除用户")
    public ResponseResult deleteUser(@RequestParam("uid") long uid){
        return userService.delete(uid);
    }

    @PostMapping("/addRole")
    @ApiOperation(value = "给用户添加角色")
    public ResponseResult addRoleToUser(@RequestParam("roleId") long roleId,@RequestParam("uid") long uid){
        return userService.addRole(uid,roleId);
    }

    @DeleteMapping("/removeRole")
    @ApiOperation(value = "移除用户某个角色")
    public ResponseResult removeRoleFromUser(@RequestParam("roleId") long roleId,@RequestParam("uid") long uid){
        return userService.removeRole(uid,roleId);
    }

}
