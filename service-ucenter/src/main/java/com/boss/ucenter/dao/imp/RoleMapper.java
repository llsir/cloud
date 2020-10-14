package com.boss.ucenter.dao.imp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boss.ucenter.entity.dto.RoleExt;
import com.boss.ucenter.entity.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-27 17:44
 */
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<RoleExt> getAllRoles();

}
