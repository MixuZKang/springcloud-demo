package com.atguigu.springcloud.FeignClient;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallback implements PaymentFeignClient {

    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(4444, "服务降级返回,----PaymentFallback", new Payment(id, "errorSerial......"));
    }
}
