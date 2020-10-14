package com.boss.cloud.dao;

import com.boss.framework.model.logs.SysLog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: lpb
 * @create: 2020-07-22 11:21
 */
public interface LogsDao extends MongoRepository<SysLog, String> {
}
