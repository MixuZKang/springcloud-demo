package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /*Feign提供了日志打印功能，我们可以通过配置来调整日志级别，
    从而了解Feign中Http请求的细节，对Feign接口的调用情况进行监控和输出

    日志级别：
        NONE：默认的，不显示任何日志；
        BASIC：仅记录请求方法、URL、响应状态码及执行时间；
        HEADERS：除了 BASIC 中定义的信息之外，还有请求和响应的头信息；
        FULL：除了 HEADERS 中定义的信息之外，还有请求和响应的正文及元数据。
    */

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
