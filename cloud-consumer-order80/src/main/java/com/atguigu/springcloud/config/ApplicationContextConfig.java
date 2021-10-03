package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    /*
    往IOC容器中注入RestTemplate：RestTemplate提供了多种便捷访问远程Http服务的方法，
    是一种简单便捷的访问restful服务的模板类，是Spring提供的用于访问Rest服务的客户端模板工具集
    */
    @Bean
    //@LoadBalanced//赋予RestTemplate负载均衡的能力
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
