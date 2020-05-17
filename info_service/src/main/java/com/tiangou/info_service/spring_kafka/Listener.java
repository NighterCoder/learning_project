package com.tiangou.info_service.spring_kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {


    public Logger logger= LoggerFactory.getLogger(Listener.class);


    //监测的主题为testflink
    @KafkaListener(topics = {"testflink"})
    public void listen(ConsumerRecord<?,?> record){
        logger.info("kafka传递消息的key："+record.key());
        logger.info("kafka传递消息的value："+record.value());
    }



}
