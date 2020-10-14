package com.boss.ucenter.dao.imp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boss.ucenter.entity.po.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: lpb
 * @create: 2020-07-27 17:43
 */
@Repository
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
