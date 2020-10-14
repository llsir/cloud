package com.boss.ucenter.dao.imp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boss.ucenter.entity.dto.PermissionExt;
import com.boss.ucenter.entity.po.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-21 11:22
 */
@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectPermissionByUserId(long userid);

    List<PermissionExt> list();

    Integer removePermission(Long pid);
}
