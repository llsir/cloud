package com.boss.gateway.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.boss.gateway.model.po.NacosGatewayProperties;
import com.boss.gateway.service.DynamicRouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

@Component
public class DynamicRouteServiceImplByNacos implements CommandLineRunner {

	@Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

	@Autowired
    private NacosGatewayProperties nacosGatewayProperties;

    /**
     * 监听Nacos Server下发的动态路由配置
     */
    public void dynamicRouteByNacosListener (){
        try {
            ConfigService configService= NacosFactory.createConfigService(nacosGatewayProperties.getAddress());
            String content = configService.getConfig(nacosGatewayProperties.getDataId(), nacosGatewayProperties.getGroupId(), nacosGatewayProperties.getTimeout());
            List<RouteDefinition> list = JSON.parseArray(content, RouteDefinition.class);
            list.forEach(definition->{
                dynamicRouteService.add(definition);
            });
            configService.addListener(nacosGatewayProperties.getDataId(), nacosGatewayProperties.getGroupId(), new Listener()  {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    List<RouteDefinition> list = JSON.parseArray(configInfo, RouteDefinition.class);
                    dynamicRouteService.refresh();
                    list.forEach(definition->{
                		dynamicRouteService.update(definition);
                	});
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
           e.printStackTrace();
        }
    }

	@Override
	public void run(String... args) throws Exception {
        dynamicRouteByNacosListener();
		
	}
    
}
