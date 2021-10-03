package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //使用该注解能够获取到配置类中的属性 8002
    @Value("${server.port}")
    private String serverPort;

    //服务发现
    @Autowired
    private DiscoveryClient discoveryClient;

    //为了前后端分离，前端不需要知道后端的具体实现，所以返回给前端的是一个Json封装体，里面封装调用信息
    //使用@RequestBody获取客户端发送请求的请求体（只有POST才有请求体），并接收json数据封装为对象
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("****插入结果：" + result);

        if (result > 0) {
            return new CommonResult(200, "插入数据成功,serverPort:" + serverPort, result);
        } else {
            return new CommonResult(404, "插入数据失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("****查询结果：" + payment);

        if (payment != null) {
            return new CommonResult(200, "查询数据成功,serverPort:" + serverPort, payment);
        } else {
            return new CommonResult(404, "查询失败，错误ID为：" + id, null);
        }
    }

    //返回当前应用端口
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }


}
