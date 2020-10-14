package com.boss.ucenter.dao.imp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boss.ucenter.entity.po.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 19:49
 */
@Repository
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    Integer deleteByRoleIdAndPermissionIds(@Param("roleId") Long roleId,@Param("pids") List<Long> pIds);
}
