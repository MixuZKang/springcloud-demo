package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    //sentinel采用的是懒加载，需要访问一次请求才会将应用加载到sentinel控制台
    @GetMapping("/testA")
    public String testA() {
        //暂停0.8秒，测试根据线程数限流
        //try { TimeUnit.MILLISECONDS.sleep(800); } catch (InterruptedException e) { e.printStackTrace(); }
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t" + "...testB");
        return "------testB";
    }

    @GetMapping("/testC")
    public String testC() {
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testC 测试RT");
        return "------testC";
    }

    @GetMapping("/testD")
    public String testD() {
        int age = 10 / 0;
        log.info("testD 测试异常比例");
        return "------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        int age = 10 / 0;
        log.info("testE 测试异常比例");
        return "------testE 测试异常比例";
    }

    @GetMapping("/testHotKey")
    //配置了@SentinelResource的接口，如果违背了在Sentinel控制台设置的规则，则会触发blockHandler指定的自定义兜底方法
    @SentinelResource(value = "testHotKey", blockHandler = "dealHandler_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "------testHotKey";
    }

    public String dealHandler_testHotKey(String p1, String p2, BlockException exception) {
        return "-----dealHandler_testHotKey";
    }


}

