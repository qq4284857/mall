package com.mall.mq.provider.send;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Send {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        String message="我发了一条消息！";
        /**
         * 发送消息
         */
        rabbitTemplate.convertAndSend("topicExchange","topic.msg",message);

    }
}
