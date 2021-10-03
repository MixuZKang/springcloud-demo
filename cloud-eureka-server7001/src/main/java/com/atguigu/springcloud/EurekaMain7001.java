package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/*Eureka的自我保护机制：
    某时刻某一个微服务不可用了，Eureka不会立刻清理，依旧会对该微服务的信息进行保存（保证高可用，Eureka属于CAP里面的AP分支）

  为什么会产生Eureka自我保护机制？
    为了防止EurekaClient可以正常运行，但是与EurekaServer网络不通情况下，EurekaServer不会立刻将EurekaClient服务剔除
    因为有可能微服务本身其实是健康的，但是网络分区故障发生(延时、卡顿、拥挤)导致微服务与EurekaServer之间无法正常通信
    此时本不应该注销这个微服务。

  当EurekaServer节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个Server节点就会进入自我保护模式，
  在自我保护模式中，EurekaServer会保护服务注册表中的信息，不再注销任何服务实例。

  自我保护模式是一种应对网络异常的安全保护措施。它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留）
  也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定。（高可用）

*/
@SpringBootApplication
@EnableEurekaServer//标注当前应用为Eureka的Server端
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class,args);
    }
}

