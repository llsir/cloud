package com.boss.ucenter.service;

import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.entity.vo.AddPermissionVO;
import com.boss.ucenter.entity.vo.EditPermissionVO;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 20:56
 */
public interface PermissionService {

    /**
     * 获取三级的权限信息
     * @return
     */
    QueryResponseResult<List> list();

    /**
     * 添加权限
     * @param addPermission 权限信息
     * @return
     */
    ResponseResult add(AddPermissionVO addPermission);

    /**
     * 级联删除权限信息
     * @param pid 权限id
     * @return
     */
    ResponseResult remove(Long pid);

    /**
     * 修改权限信息
     * @param editPermission 权限信息
     * @return
     */
    ResponseResult edit(EditPermissionVO editPermission);
}
