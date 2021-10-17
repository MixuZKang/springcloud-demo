package com.atguigu.springcloud.service;

import org.apache.ibatis.annotations.Param;

public interface StorageService {
    /**
     * 扣减库存
     */
    void decrease(Long productId, Integer count);
}
