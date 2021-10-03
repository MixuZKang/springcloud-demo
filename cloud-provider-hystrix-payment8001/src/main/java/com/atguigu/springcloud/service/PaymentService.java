package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    //正常访问，一切OK
    public String paymentInfo_OK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + "paymentInfo_OK,id: " + id + "\t" + "O(∩_∩)O";
    }

    //超时访问，用于演示降级（异常、超时、熔断都会引发降级）一般服务降级都是在消费端进行
    //使用@HystrixCommand进行服务降级，fallbackMethod指定超时/异常后的处理方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            //设置自身调用超时时间的峰值为5000毫秒（默认1000毫秒），峰值内可以正常运行，超过则交给兜底的方法处理，作服务降级fallback
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        //int age = 10 / 0;
        int timeNumber = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + "id: " + id + "\t" + "O(∩_∩)O调用成功";
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "/(ㄒoㄒ)/调用8001支付接口超时或异常：\t" + "\t当前线程池名字：" + Thread.currentThread().getName();
    }

    /*演示服务熔断
        断路器开启后，经过几次尝试后发现能够正常运行则【恢复调用链路】，服务降级->进而熔断->恢复调用链路
        熔断类型：
            熔断打开open：请求不再进行调用当前服务，内部设置时钟一般为MTTR（平均故障处理时间)，当打开时长达到所设时钟则进入半熔断状态
            熔断半开half-open：部分请求根据规则调用当前服务，如果请求成功且符合规则则认为当前服务恢复正常，关闭熔断
            熔断关闭closed：熔断关闭后不会对服务进行熔断
        以下配置含义：假设在时间窗口期 10秒钟内，10次请求有6次都是失败（异常/超时）的则跳闸开启断路器（失败率60%开启断路器）
            当断路器开启后，所有请求都不会进行转发，再有请求调用的时候，将不会调用主逻辑，而是直接调用降级fallback；
            一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发，如果成功则断路器会关闭，若失败则继续开启。
    */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数阈值（默认20）
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期（单位毫秒，默认为10秒）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),//失败率达到多少后跳闸（默认50%）

    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id不能为负数******");
        }
        //使用HuTool工具包生成随机UUID
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号: " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id不能为负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
    }


}
