package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //通过SpringCloud原生注解@RefreshScope让Nacos也实现配置动态刷新
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    /*
    获取Nacos中的配置，启动前需要在Nacos客户端-配置管理-配置管理栏目下有生成对应DataId的配置文件

    Nacos配置的DataId规则，由以下几项配置组成：
        ${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
        根据上述当前项目配置DataId为：nacos-config-client-dev.yaml

    Nacos配置的多级分类，由大到小：Namespace--Group--DataId
    */
    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
