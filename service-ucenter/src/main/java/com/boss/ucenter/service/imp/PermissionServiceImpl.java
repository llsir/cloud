package com.boss.ucenter.service.imp;

import com.boss.framework.model.response.CommonCode;
import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;
import com.boss.ucenter.dao.imp.PermissionMapper;
import com.boss.ucenter.entity.dto.PermissionExt;
import com.boss.ucenter.entity.po.Permission;
import com.boss.ucenter.entity.vo.AddPermissionVO;
import com.boss.ucenter.entity.vo.EditPermissionVO;
import com.boss.ucenter.service.PermissionService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 20:57
 */
@Repository
@Mapper
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    /**
     * 获取三级的权限信息
     * @return
     */
    @Override
    public QueryResponseResult<List> list() {
        List<PermissionExt> list = permissionMapper.list();
        return new QueryResponseResult<List>(CommonCode.SUCCESS,list);
    }

    /**
     * 添加权限
     * @param addPermission 权限信息
     * @return
     */
    @Override
    public ResponseResult add(AddPermissionVO addPermission) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(addPermission,permission);
        permission.setStatus("1");
        permission.setCreateTime(new Date());
        permissionMapper.insert(permission);
        return ResponseResult.SUCCESS();
    }

    /**
     * 级联删除权限信息
     * @param pid 权限id
     * @return
     */
    @Override
    @Transactional
    public ResponseResult remove(Long pid) {
        Permission permission = permissionMapper.selectById(pid);
        if(permission == null){
            return new ResponseResult(CommonCode.PERMISSION_NOEXIST);
        }
        Integer num = permissionMapper.removePermission(pid);
        return ResponseResult.SUCCESS();
    }

    /**
     * 修改权限信息
     * @param editPermission 权限信息
     * @return
     */
    @Override
    public ResponseResult edit(EditPermissionVO editPermission) {
        Permission permission = permissionMapper.selectById(editPermission.getId());
        if(permission == null){
            return new ResponseResult(CommonCode.PERMISSION_NOEXIST);
        }
        BeanUtils.copyProperties(editPermission,permission);
        permission.setUpdateTime(new Date());
        permissionMapper.updateById(permission);
        return ResponseResult.SUCCESS();
    }
}
