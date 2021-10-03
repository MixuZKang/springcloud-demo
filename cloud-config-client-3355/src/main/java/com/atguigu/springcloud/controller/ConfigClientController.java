package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//伪动态刷新，能够避免重启项目，但需要在控制台手动发送请求 curl -X POST "http://localhost:3355/actuator/refresh"
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    //访问config server暴露的API接口，读取配置
    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }
}

