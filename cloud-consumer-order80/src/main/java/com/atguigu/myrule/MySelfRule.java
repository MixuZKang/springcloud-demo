package com.atguigu.myrule;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//想要自定义Ribbon的负载均衡机制，配置类不能放在能够被@ComponentScan所扫描的当前包下以及子包下（即com.atguigu.springcloud包）
@Configuration
public class MySelfRule {

    /*IRule是Ribbon中的一个接口，它能够根据特定算法中从服务列表中选取一个要访问的服务
        com.netflix.loadbalancer.RoundRobinRule： 轮询
        com.netflix.loadbalancer.RandomRule：随机
        com.netflix.loadbalancer.RetryRule：先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务
        WeightedResponseTimeRule：对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择
        BestAvailableRule：会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
        AvailabilityFilteringRule：先过滤掉故障实例，再选择并发较小的实例
        ZoneAvoidanceRule：默认规则,复合判断server所在区域的性能和server的可用性选择服务器
    */
    @Bean
    public IRule myRule() {
        //将负载均衡机制定义为随机
        return new RandomRule();
    }
}
