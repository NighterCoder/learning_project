package com.tiangou.info_service.springboot_kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka主题信息监控
 */
@Component
public class TestConsumer {

    @KafkaListener(topics = {"flinkTest1"})
    public void listen(ConsumerRecord<?,?> record){
        System.out.printf("topic=%s,offset=%d,value=%s",record.topic(),record.offset(),record.value());

    }

}
