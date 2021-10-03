package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {
    //添加
    public int create(Payment payment);

    //根据id查询
    public Payment getPaymentById(@Param("id") Long id);
}
