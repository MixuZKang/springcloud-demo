package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;


//将channel和exchange绑定，可以理解为定义一个发布消息的信道
@EnableBinding(Source.class)
//发布消息接口的实现类
public class MessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;//声明发布消息的信道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        //构建消息并发送，withPayload()中传递消息体
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("***serial: " + serial);
        return serial;
    }
}

