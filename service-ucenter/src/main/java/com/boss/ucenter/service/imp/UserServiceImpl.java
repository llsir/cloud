package com.boss.ucenter.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boss.framework.model.response.CommonCode;
import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.QueryResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.dao.imp.PermissionMapper;
import com.boss.ucenter.dao.imp.RoleMapper;
import com.boss.ucenter.dao.imp.UserMapper;
import com.boss.ucenter.dao.imp.UserRoleMapper;
import com.boss.ucenter.entity.dto.UserExt;
import com.boss.ucenter.entity.po.Permission;
import com.boss.ucenter.entity.po.Role;
import com.boss.ucenter.entity.po.User;
import com.boss.ucenter.entity.po.UserRole;
import com.boss.ucenter.entity.vo.QueryUserRequestVO;
import com.boss.ucenter.entity.vo.UserInfo;
import com.boss.ucenter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lpb
 * @create: 2020-07-21 11:04
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public UserExt getUserExt(String userName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",userName);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            return null;
        }
        long userId = user.getId();
        List<Permission> menus = permissionMapper.selectPermissionByUserId(userId);
        UserExt userExt = new UserExt();
        BeanUtils.copyProperties(user,userExt);
        userExt.setPermissions(menus);
        return userExt;
    }

    /**
     * 获取所有用户的信息
     * @param page             页码
     * @param size             每页显示数量
     * @param queryUserRequest 查询参数
     * @return
     */
    @Override
    @Transactional
    public QueryResponseResult<QueryResult> list(Integer page, Integer size, QueryUserRequestVO queryUserRequest) {
        if(queryUserRequest == null){
            queryUserRequest = new QueryUserRequestVO();
        }
        QueryWrapper queryWrapper = wrapRequest(queryUserRequest);
        if(page <= 0){
            page = 1;
        }
        if(size <= 0){
            size = 5;
        }
        Page<User> pageInfo = new Page<>(page, size);
        IPage<User> result = userMapper.selectPage(pageInfo, queryWrapper);
        long total = result.getTotal();
        List users = result.getRecords();
        QueryResult<User> queryResult = new QueryResult<>();
        queryResult.setList(users);
        queryResult.setTotal(total);
        return new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
    }

    /**
     * 获取单个用户的信息
     * @param uid 用户id
     * @return
     */
    @Override
    public QueryResponseResult<User> getOne(Long uid) {
        User user = userMapper.selectById(uid);
        return new QueryResponseResult<>(CommonCode.SUCCESS,user);
    }

    /**
     * 将查询条件进行包装
     * @param queryUserRequest 查询条件
     * @return
     */
    private QueryWrapper wrapRequest(QueryUserRequestVO queryUserRequest){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(queryUserRequest.getUsername())){
            queryWrapper.eq("username",queryUserRequest.getUsername());
        }
        if(!StringUtils.isEmpty(queryUserRequest.getPhone())){
            queryWrapper.like("phone",queryUserRequest.getPhone());
        }
        if(!StringUtils.isEmpty(queryUserRequest.getUtype())){
            queryWrapper.eq("utype",queryUserRequest.getUtype());
        }
        if(!StringUtils.isEmpty(queryUserRequest.getStatus())){
            queryWrapper.eq("status",queryUserRequest.getStatus());
        }
        if(!StringUtils.isEmpty(queryUserRequest.getName())){
            queryWrapper.like("name",queryUserRequest.getName());
        }
        return queryWrapper;
    }

    @Override
    @Transactional
    public ResponseResult add(UserInfo userInfo) {
        Map<String,Object> condition = new HashMap<>();
        if(StringUtils.isEmpty(userInfo.getUsername())){
            return new ResponseResult(CommonCode.USERNAME_ISEMPTY);
        }
        condition.put("username",userInfo.getUsername());
        List<User> users = userMapper.selectByMap(condition);
        if(users.size() != 0){
            return new ResponseResult(CommonCode.USERNAME_HASEXISTS);
        }
        if(!StringUtils.isEmpty(userInfo.getPassword())){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        }
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        user.setCreateTime(new Date());
        userMapper.insert(user);
        return ResponseResult.SUCCESS();
    }

    @Override
    @Transactional
    public ResponseResult edit(UserInfo userInfo) {
        User User = userMapper.selectById(userInfo.getId());
        if(User == null){
            return new ResponseResult(CommonCode.USER_NOTEXISTS);
        }
        BeanUtils.copyProperties(userInfo,User);
        User.setUpdateTime(new Date());
        userMapper.updateById(User);
        return ResponseResult.SUCCESS();
    }

    @Override
    @Transactional
    public ResponseResult delete(long uid) {
        User user = userMapper.selectById(uid);
        if(user == null){
            return new ResponseResult(CommonCode.USER_NOTEXISTS);
        }
        user.setStatus("103004");
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        return ResponseResult.SUCCESS();
    }

    @Override
    @Transactional
    public ResponseResult addRole(long uid, long roleId) {
        User user = userMapper.selectById(uid);
        if(user == null){
            return new ResponseResult(CommonCode.USER_NOTEXISTS);
        }
        Role role = roleMapper.selectById(roleId);
        if(role == null){
            return new ResponseResult(CommonCode.ROLE_NOTEXISTS);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",uid);
        map.put("role_id",roleId);
        List<UserRole> userRoles = userRoleMapper.selectByMap(map);
        if(userRoles.size() > 0){
            return new ResponseResult(CommonCode.USER_ROLE_HASEXIST);
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(uid);
        userRole.setRoleId(roleId);
        userRole.setCreateTime(new Date());
        userRoleMapper.insert(userRole);
        return ResponseResult.SUCCESS();
    }

    /**
     * 移除指定用户的角色
     * @param uid    用户id
     * @param roleId 角色id
     * @return
     */
    @Override
    @Transactional
    public ResponseResult removeRole(long uid, long roleId) {
        User user = userMapper.selectById(uid);
        if(user == null){
            return new ResponseResult(CommonCode.USER_NOTEXISTS);
        }
        Role role = roleMapper.selectById(roleId);
        if(role == null){
            return new ResponseResult(CommonCode.ROLE_NOTEXISTS);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",uid);
        map.put("role_id",roleId);
        List<UserRole> userRoles = userRoleMapper.selectByMap(map);
        userRoleMapper.deleteByMap(map);
        return ResponseResult.SUCCESS();
    }

}
