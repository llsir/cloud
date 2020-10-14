package com.boss.cloud.service.imp;

import com.boss.cloud.dao.LogsDao;
import com.boss.cloud.po.QueryLogRequest;
import com.boss.cloud.service.LogService;
import com.boss.framework.model.logs.SysLog;
import com.boss.framework.model.response.CommonCode;
import com.boss.framework.model.response.QueryResponseResult;
import com.boss.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-22 11:23
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogsDao logsDao;

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public ResponseResult save(SysLog sysLog){
        SysLog save = logsDao.save(sysLog);
        return ResponseResult.SUCCESS();
    }

    @Override
    public QueryResponseResult<List> list(int page, int size, QueryLogRequest queryLogRequest) {
        if(queryLogRequest == null){
            queryLogRequest = new QueryLogRequest();
        }
        if(page <= 0){
            page = 1;
        }
        page--;
        if(size <= 0){
            size = 10;
        }
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(!StringUtils.isEmpty(queryLogRequest.getMessage())){
            criteria.and("message").regex(queryLogRequest.getMessage());
        }
        if(!StringUtils.isEmpty(queryLogRequest.getType())){
            criteria.and("type").is(queryLogRequest.getType());
        }
        //gte >=; lte <=
        if(!StringUtils.isEmpty(queryLogRequest.getStartTime()) && !StringUtils.isEmpty(queryLogRequest.getEndTime())){
            criteria.and("createTime").gte(queryLogRequest.getStartTime()).lte(queryLogRequest.getEndTime());
        }else if(!StringUtils.isEmpty(queryLogRequest.getStartTime())){
            criteria.and("createTime").gte(queryLogRequest.getStartTime());
        }else if(!StringUtils.isEmpty(queryLogRequest.getEndTime())){
            criteria.and("createTime").lte(queryLogRequest.getEndTime());
        }
        query.addCriteria(criteria);
        PageRequest pageRequest = new PageRequest(page, size);
        query.with(pageRequest);
        List<SysLog> logs = mongoTemplate.find(query, SysLog.class);
        return new QueryResponseResult<List>(CommonCode.SUCCESS,logs);
    }
}
