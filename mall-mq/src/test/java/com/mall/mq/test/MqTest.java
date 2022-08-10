package com.mall.mq.test;

import com.mall.mq.provider.send.Send;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MqTest {
    @Autowired
    Send send;


    @Test
    public void test(){

        send.send();
    }
}
