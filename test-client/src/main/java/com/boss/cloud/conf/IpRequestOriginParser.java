package com.boss.cloud.conf;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlot;
import com.alibaba.csp.sentinel.slots.block.flow.FlowSlot;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lpb
 * @create: 2020-08-13 17:19
 */
@Component
public class IpRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {

        httpServletRequest.getRemoteAddr();
        return httpServletRequest.getParameter("origin");
    }
}
