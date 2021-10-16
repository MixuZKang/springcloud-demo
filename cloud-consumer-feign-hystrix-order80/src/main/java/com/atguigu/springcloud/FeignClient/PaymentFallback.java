package com.atguigu.springcloud.FeignClient;

import org.springframework.stereotype.Component;

@Component
//如果在controller中定义Fallback方法会导致代码高耦合，可以专门定义一个处理Fallback的类
public class PaymentFallback implements PaymentHystrixFeignClient {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "服务调用失败，提示来自：cloud-consumer-feign-order80-paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "服务调用失败，提示来自：cloud-consumer-feign-order80-paymentInfo_TimeOut";
    }
}
