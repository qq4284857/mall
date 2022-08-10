package com.mall.manager.mallmanager.publish.recv;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
/**
 * 发布订阅模式
 */
public class Recv02 {

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
        Connection connection = factory.newConnection();
        //获取通道
        Channel channel = connection.createChannel();
//一次只能接收一条，处理完成后才能发第二条
//        channel.basicQos(1);
        //申明队列
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.exchangeDeclare(EXCHANGES_NAME, BuiltinExchangeType.FANOUT);

        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,EXCHANGES_NAME,"");


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            //手动回执
            //channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        //监听队列
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> { });
    }
}