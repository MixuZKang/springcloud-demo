package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;


@RestController
@Slf4j
public class OrderController {

    //订单服务调用地址不能写死，单机版可以这样写但是集群模式下不行
    //public static final String PaymentSrv_URL = "http://localhost:8001";

    //通过在Eureka上注册的微服务名称来调用
    public static final String PAYMENT_SRV = "http://CLOUD-PAYMENT-SERVICE";

    //通过RestTemplate进行远程调用
    @Autowired
    private RestTemplate restTemplate;

    //服务发现
    @Autowired
    private DiscoveryClient discoveryClient;

    //引入自定义的负载均衡策略的接口 com.atguigu.springcloud.lb.LoadBalancer
    @Autowired
    private LoadBalancer loadBalancer;

    //客户端用浏览器访问所以是get请求，但是底层实质发送的是post请求调用服务端8001
    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        //因为实际发送的是post请求，所以使用postForObject，传入REST请求地址、请求参数、HTTP响应转换被转换成的对象类型
        return restTemplate.postForObject(PAYMENT_SRV + "/payment/create", payment, CommonResult.class);

        //返回ResponseEntity对象的形式：
        //return restTemplate.postForEntity(PAYMENT_SRV + "/payment/create", payment, CommonResult.class).getBody();
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment01(@PathVariable("id") Long id) {
        //传入REST请求地址、HTTP响应转换被转换成的对象类型
        return restTemplate.getForObject(PAYMENT_SRV + "/payment/get/" + id, CommonResult.class);
    }

    //getForObject:返回对象为响应体中数据转化成的对象，基本上可以理解为Json
    //getForEntity:返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等
    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment02(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_SRV + "/payment/get/" + id, CommonResult.class);
        //如果返回的状态码为2xx，即成功调用
        if (entity.getStatusCode().is2xxSuccessful()) {
            //获取请求体并返回
            return entity.getBody();
        } else {
            return new CommonResult<>(404, "操作失败");
        }
    }

    //测试负载均衡
    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB() {
        //通过指定服务名称获取该名称下所有微服务的集合 -->  payment8002,payment8001
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        //调用自定义的负载均衡算法并返回服务实例
        ServiceInstance serviceInstance = loadBalancer.getInstances(instances);
        URI uri = serviceInstance.getUri();
        //通过获取到的uri远程调用服务 8001或8002
        return restTemplate.getForObject(uri + "/payment/lb", String.class);

    }

    //测试zipkin+sleuth
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject("http://localhost:8001" + "/payment/zipkin/", String.class);
        return result;
    }

}
