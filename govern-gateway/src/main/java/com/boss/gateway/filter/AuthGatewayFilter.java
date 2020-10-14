package com.boss.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.boss.gateway.model.response.CommonCode;
import com.boss.gateway.model.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author: lpb
 * @create: 2020-07-28 14:04
 */
@Slf4j
public class AuthGatewayFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        List<HttpCookie> cookie = cookies.get("uid");
        HttpCookie httpCookie = cookie.get(0);
        String access_token = httpCookie.getValue();
        if(access_token == null){
            //拒绝访问
            return access_denied(exchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> access_denied(ServerWebExchange exchange){

        ServerHttpResponse response = exchange.getResponse();
        ResponseResult responseResult = new ResponseResult(CommonCode.UNAUTHENTICATED);
        byte[] bytes = JSON.toJSONString(responseResult).getBytes();
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
