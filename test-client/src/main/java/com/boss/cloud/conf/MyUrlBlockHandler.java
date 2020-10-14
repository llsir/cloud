package com.boss.cloud.conf;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.boss.cloud.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 针对url限流时的处理类
 * @author: lpb
 * @create: 2020-08-14 10:56
 */
@Component
@Slf4j
public class MyUrlBlockHandler implements UrlBlockHandler {

    /**
     * Handle the request when blocked.
     * @param request  Servlet request
     * @param response Servlet response
     * @param ex       the block exception.
     * @throws IOException some error occurs
     */
    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws IOException {

        log.info("Blocked by Sentinel:{}",ex.getClass().getSimpleName());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(ResponseResult.FAIL()));
        out.flush();
        out.close();
    }
}
