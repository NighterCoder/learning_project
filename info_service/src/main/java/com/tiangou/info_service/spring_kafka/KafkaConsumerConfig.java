package com.tiangou.info_service.spring_kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka的消费者
 * Created by 凌战 on 2019/1/14
 */

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

        //理论上consumer读取kafka应该通过zookeeper，但是这里用的是kafka server地址
        @Value("${spring.kafka.consumer.bootstrap-servers}")
        private String servers;
        @Value("${spring.kafka.consumer.enable-auto-commit}")
        private boolean enableAutoCommit;
        @Value("${spring.kafka.consumer.session.timeout}")
        private String sessionTimeout;
        @Value("${spring.kafka.consumer.auto-commit-interval}")
        private String autoCommitInterval;
        @Value("${spring.kafka.consumer.group-id}")
        private String groupId;
        @Value("${spring.kafka.consumer.auto-offset-reset}")
        private String autoOffsetReset;
        @Value("${spring.kafka.listener.concurrency}")
        private int concurrency;


        //返回Kafka监听器容器工厂
        @Bean
        public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> kafkaListenerContainerFactory(){
             ConcurrentKafkaListenerContainerFactory<String,String> factory=new ConcurrentKafkaListenerContainerFactory<>();
             //设置并行度为10，那么就是相当于有10个ConcurrentMessageListenerContainer
             //一般来说，这个并行度与主题的分区数保持一致，即一个消费者消费一个分区
             factory.setConcurrency(concurrency);
             factory.setConsumerFactory(consumerFactory());
             factory.getContainerProperties().setPollTimeout(1500);
             return factory;
        }


        //创建ConsumerFactory bean
        public ConsumerFactory<String,String> consumerFactory(){
                return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }


        public Map<String,Object> consumerConfigs(){
              Map<String,Object>   props=new HashMap<>();
              props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,servers);
              props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,enableAutoCommit);
              props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,autoCommitInterval);
              props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,sessionTimeout);
              props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
              props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
              props.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
              props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,autoOffsetReset);
              return props;
        }

        @Bean
        public Listener listener(){
                return new Listener();
        }



        public void  test(){
            kafkaListenerContainerFactory().createContainer().getContainers();
//            kafkaListenerContainerFactory().createContainer().setupMessageListener(new MessageListener<>() {
//            });
        }


}
