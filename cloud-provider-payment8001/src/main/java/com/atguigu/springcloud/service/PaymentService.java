package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    //添加
    public int create(Payment payment);

    //根据id查询
    public Payment getPaymentById(@Param("id") Long id);
}
