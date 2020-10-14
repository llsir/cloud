package com.boss.cloud.service;

import com.boss.cloud.po.QueryLogRequest;
import com.boss.framework.model.logs.SysLog;
import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-22 11:38
 */
public interface LogService {


    /**
     * 保存日志信息
     * @param sysLog 日志相关信息
     * @return 统一响应结果
     */
    ResponseResult save(SysLog sysLog);

    /**
     * 分页查询符合条件的日志记录
     * @param page 页码
     * @param size 每页记录数
     * @param queryLogRequest 查询条件，可以为空
     * @return 统一查询结果
     */
    QueryResponseResult<List> list(int page, int size, QueryLogRequest queryLogRequest);
}
