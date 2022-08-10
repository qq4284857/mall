package com.mall.manager.mallmanager.routing.recv;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
/**
 * 路由模式
 */
public class Recv01 {

    private final static String QUEUE_NAME = "hello";
private final static String EXCHANGES_NAME = "EXCHANGES";
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
        //一次只能接收一条，处理完成后才能发第二条
        channel.basicQos(1);
       channel.exchangeDeclare(EXCHANGES_NAME, BuiltinExchangeType.DIRECT);
        //申明队列
       //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //获取队列名称
        //String queue = channel.queueDeclare().getQueue();
        channel.queueBind(QUEUE_NAME,EXCHANGES_NAME,"info");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");


        };

        //监听队列
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}