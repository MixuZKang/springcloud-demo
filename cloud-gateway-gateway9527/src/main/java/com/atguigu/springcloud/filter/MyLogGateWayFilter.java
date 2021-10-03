package com.atguigu.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
//自定义过滤器，可以用于全局日志记录、统一网关鉴权等
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("********** come in MyLogGateWayFilter:" + new Date());
        //得到请求参数 uname
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        //如果指定参数 uname为空
        if (uname == null) {
            log.info("********* 用户名为null，无法登录");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);//设置状态码为 “不被接受”
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);//放行
    }

    @Override
    public int getOrder() {
        return 0;//加载Filter过滤器的顺序，数字越小优先级越高
    }
}
