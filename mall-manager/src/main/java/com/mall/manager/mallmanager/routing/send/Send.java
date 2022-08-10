package com.mall.manager.mallmanager.routing.send;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 路由模式
 */
public class Send {

//    private final static String QUEUE_NAME = "hello";
     private final static String EXCHANGES_NAME = "EXCHANGES";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("124.70.188.60");
        factory.setPort(5672);
        factory.setUsername("op");
        factory.setPassword("op");
        factory.setVirtualHost("/op");
        //通过工厂获取连接
        try (Connection connection = factory.newConnection();
             //获取通道
             Channel channel = connection.createChannel()) {
            //申明队列
            //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //交换机绑定  广播模式
            channel.exchangeDeclare(EXCHANGES_NAME, BuiltinExchangeType.DIRECT);
           // String erormessage = "Hello World!";
            String infomessage = "Hello World!";
            //发送消息放进交换机
            //channel.basicPublish(EXCHANGES_NAME,"error",null,erormessage.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGES_NAME,"info",null,infomessage.getBytes(StandardCharsets.UTF_8));
            //发送消息
           // channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            //System.out.println(" [x] Sent '" + message + "'");
        }
    }
}