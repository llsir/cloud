package com.boss.ucenter.service;

import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.QueryResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.entity.dto.UserExt;
import com.boss.ucenter.entity.po.User;
import com.boss.ucenter.entity.vo.QueryUserRequestVO;
import com.boss.ucenter.entity.vo.UserInfo;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-21 11:03
 */
public interface UserService {

    /**
     * 得到用户全部信息，包括用户权限
     * @param userName 用户名
     * @return 用户信息
     */
    UserExt getUserExt(String userName);

    /**
     * 获取所有用户的信息
     * @param page 页码
     * @param size 每页显示数量
     * @param queryUserRequest 查询参数
     * @return
     */
    QueryResponseResult<QueryResult> list(Integer page, Integer size, QueryUserRequestVO queryUserRequest);

    /**
     * 获取单个用户的信息
     * @param uid 用户id
     * @return
     */
    QueryResponseResult<User> getOne(Long uid);

    /**
     * 添加用户
     * @param userInfo 用户基本信息
     * @return
     */
    ResponseResult add(UserInfo userInfo);

    /**
     * 修改用户信息
     * @param userInfo 用户信息
     * @return
     */
    ResponseResult edit(UserInfo userInfo);

    /**
     * 删除用户，假删除，只修改用户状态
     * @param uid 用户id
     * @return
     */
    ResponseResult delete(long uid);

    /**
     * 给指定用户添加某一角色
     * @param uid 用户id
     * @param roleId 角色id
     * @return
     */
    ResponseResult addRole(long uid, long roleId);

    /**
     * 移除指定用户的角色
     * @param uid 用户id
     * @param roleId 角色id
     * @return
     */
    ResponseResult removeRole(long uid, long roleId);


}
