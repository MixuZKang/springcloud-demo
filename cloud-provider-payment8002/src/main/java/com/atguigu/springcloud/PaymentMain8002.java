package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient//标注当前应用为Eureka的Client端（该服务Payment作为提供者）
@EnableDiscoveryClient//服务发现
public class PaymentMain8002 {
    
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8002.class, args);
    }
}
