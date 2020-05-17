package com.tiangou.info_service.spring_kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Kafka的生产者
 * Created by 凌战 on 2019/1/6
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrap_servers;  //broker地址

    @Value("${spring.kafka.producer.retries}")
    private int retries;


    @Value("${spring.kafka.producer.batch-size}")
    private int batch_size;

    @Value("${spring.kafka.producer.buffer-memory}")
    private int buffer_memory;


    //KAFKA PRODUCER 连接相关参数
    public Map<String,Object> producerConfigs(){
        Map<String,Object> props=new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrap_servers);
        props.put(ProducerConfig.RETRIES_CONFIG,retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,batch_size);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,buffer_memory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return props;
    }



    public ProducerFactory<String,String> producerFactory(){
        return new DefaultKafkaProducerFactory<String,String>(producerConfigs());
    }


    /**
     * 负责发送消息的角色，调用send()方法即可发送消息至Kafka Broker
     * @return KafkaTemplate
     */
    @Bean(name="kafkaTemplate")
    public KafkaTemplate<String,String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }





}
