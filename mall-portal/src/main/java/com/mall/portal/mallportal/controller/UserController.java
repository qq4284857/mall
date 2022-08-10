package com.mall.portal.mallportal.controller;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RabbitListener(queues = "topic")
public class UserController {

    @RabbitHandler
    public void recv(String msg) {
        //msg 为队列中存储的json字符串
        System.out.println(msg);
    }

}
