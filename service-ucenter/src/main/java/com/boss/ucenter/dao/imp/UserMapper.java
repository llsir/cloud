package com.boss.ucenter.dao.imp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boss.ucenter.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: lpb
 * @create: 2020-07-21 11:02
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
