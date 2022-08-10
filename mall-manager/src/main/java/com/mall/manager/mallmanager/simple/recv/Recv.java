package com.mall.manager.mallmanager.simple.recv;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("124.70.188.60");
        factory.setPort(5672);
        factory.setUsername("op");
        factory.setPassword("op");
        factory.setVirtualHost("/op");
        //通过工厂获取连接
        Connection connection = factory.newConnection();
        //获取通道
        Channel channel = connection.createChannel();

        //申明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}