package com.atguigu.springcloud.lb;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
负载均衡算法（轮询）：Rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标，当服务重启动后Rest接口计数从1开始重新计数

  List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
    如：  List [0] instances = 127.0.0.1:8002
　　　    List [1] instances = 127.0.0.1:8001
    8001 + 8002 组合成为集群，它们共计2台机器，集群总数为2， 按照轮询算法原理：

    当总请求数为1时： 1 % 2 =1 对应下标位置为1 ，则获得服务地址为127.0.0.1:8001
    当总请求数位2时： 2 % 2 =0 对应下标位置为0 ，则获得服务地址为127.0.0.1:8002
    当总请求数位3时： 3 % 2 =1 对应下标位置为1 ，则获得服务地址为127.0.0.1:8001
    当总请求数位4时： 4 % 2 =0 对应下标位置为0 ，则获得服务地址为127.0.0.1:8002
*/

//手写负载均衡算法（轮询）
@Component
public class MyLB implements LoadBalancer {

    //给定一个初始值为0
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //计算出当前为Rest接口的第几次请求数
    public final int getAndIncrement() {
        int current;//当前值
        int next;//下一值
        do {
            //初始时设当前值为0
            current = this.atomicInteger.get();
            //如果当前值在int最大范围内，则把当前值 +1并赋给next
            next = current >= 2147483647 ? 0 : current + 1;
        //调用compareAndSet()比较并替换，传入期望值，替换后的值
        //如果当前值跟期望值比较后相等则修改当前值并返回!true，即false跳出循环；如果不相等则一直循环，直到满足期望值
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("当前是第" + next + "次访问");
        return next;
    }

    @Override
    public ServiceInstance getInstances(List<ServiceInstance> serviceInstances) {
        //实际调用服务器位置下标 = Rest接口第几次请求数 % 服务器集群总数量
        int index = getAndIncrement() % serviceInstances.size();
        //根据下标获取具体服务器实例并返回
        return serviceInstances.get(index);

    }
}
