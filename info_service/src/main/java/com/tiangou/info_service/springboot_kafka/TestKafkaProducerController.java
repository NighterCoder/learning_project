package com.tiangou.info_service.springboot_kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springboot-kafka")
public class TestKafkaProducerController {



    @Autowired
    private KafkaTemplate kafkaTemplate;


    //send()方法有多个重载的方法
    //每个使用到KafkaTemplate对象的方法都会是一个生产者
    @RequestMapping("/send")
    public void send(@RequestParam("msg") String msg){
        ListenableFuture future=kafkaTemplate.send("input",msg);
        future.addCallback(res-> System.out.println("send-消息发送成功"),throwable -> System.out.println("send-消息发送失败"));

    }




}
