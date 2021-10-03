package com.atguigu.springcloud.FeignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component
//使用fallback指定当调用超时/异常时交给哪个类进行处理
//使用服务降级让客户端在服务端不可用时（异常/超时/宕机）也会获得提示信息而不会挂起耗死服务器
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentFallback.class)
public interface PaymentHystrixFeignClient {

    @GetMapping("/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentInfo_TimeOut(@PathVariable("id") Integer id);

}
