package com.boss.cloud.controller;

import com.boss.cloud.po.QueryLogRequest;
import com.boss.cloud.service.LogService;
import com.boss.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-22 11:40
 */
@RestController
@RequestMapping("/log")
@Api(value = "日志管理")
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("/view/{page}/{size}")
    @ApiOperation(value = "根据指定条件获取日志信息")
    public QueryResponseResult<List> getLogs(@PathVariable("page")int page, @PathVariable("size")int size, QueryLogRequest queryLogRequest){
        return logService.list(page,size,queryLogRequest);
    }


}
