package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderNacosController {
    @Resource
    private RestTemplate restTemplate;

    //通过在Nacos上注册的微服务名称来调用
    //public static final String SERVICE_URL = "http://nacos-payment-provider";

    //引用yml配置文件中定义的提供者微服务名称
    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    //Nacos自带负载均衡调用（内部集成了Ribbon：RestTemplate+负载均衡调用）
    @GetMapping("/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Long id) {
        return restTemplate.getForObject(serverURL + "/payment/nacos/" + id, String.class);
    }

}
