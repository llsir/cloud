package com.boss.cloud;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * @author: lpb
 * @create: 2020-08-13 16:16
 */
public class DTest {

    @Test
    public void test1(){
        String a = "{\"burstCount\":0,\"clusterConfig\":{\"fallbackToLocalWhenFail\":true,\"sampleCount\":10,\"thresholdType\":0,\"windowIntervalMs\":1000},\"clusterMode\":false,\"controlBehavior\":0,\"count\":2.0,\"durationInSec\":2,\"grade\":1,\"limitApp\":\"default\",\"maxQueueingTimeMs\":0,\"paramFlowItemList\":[],\"paramIdx\":0,\"resource\":\"/hello/aa\"}";
        ParamFlowRule paramFlowRule = JSON.parseObject(a, ParamFlowRule.class);
        System.out.println(paramFlowRule.getParamIdx());
    }
}
