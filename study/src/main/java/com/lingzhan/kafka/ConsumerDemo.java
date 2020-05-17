package com.lingzhan.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 *
 * Kafka Consumer 不是 thread-safe
 * 2.3 版本 可以交互的broker 最低版本是0.10.0
 *
 * 每条记录在partition中都有对应的offset
 * consumer带有offset为n,说明已经消费信息n-1条
 * Created by 凌战 on 2019/12/11
 */
public class ConsumerDemo {

    private static final String TOPIC="test";


    public static void main(String[] args) {

        KafkaConsumer<String,String> consumer= initConsumerAuto();
        consumerWithAutoOffset(consumer,TOPIC);



    }



    public static KafkaConsumer<String,String> initConsumerAuto(){
        System.setProperty("java.security.auth.login.config", "C:\\resources\\workspace\\tiangou\\flink_analysis\\flink_study\\src\\main\\resources\\kafka_jass.conf");
        Properties props=new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localnode2:9094");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"test-acl");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        return new KafkaConsumer<String, String>(props);

    }


    public static KafkaConsumer<String,String> initConsumerMannual(){

        Properties props=new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"hnode14:9092");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG,"test-lingzhan");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);

        return new KafkaConsumer<String, String>(props);

    }

    // Automatic Offset Committing
    // 通过 enable.auto.commit 属性,提交频次 auto.commit.interval.ms
    public static void consumerWithAutoOffset(KafkaConsumer consumer,String topic){
        consumer.subscribe(Collections.singletonList(topic));
        while (true){
            ConsumerRecords<String,String> records=consumer.poll(100);  //毫秒
            for (ConsumerRecord<String,String> record:records){
                System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n",record.partition(), record.offset(), record.key(), record.value());
            }

        }
    }


    // Manual Offset Control
    // 代替consumer自动提交,用户可以手动提交(在认为消息被消费时)
    // 适用场景: 当消息的消费与一些处理逻辑相耦合,等处理逻辑全部完成才能被认为提交
    public static void consumerWithManualOffset(KafkaConsumer consumer,String topic) {
        consumer.subscribe(Collections.singletonList(topic));
        final int minBatchSize=20;
        List<ConsumerRecord<String,String>> buffer=new ArrayList<>();
        while (true){
            ConsumerRecords<String,String> records=consumer.poll(100);
            for (ConsumerRecord<String,String> record:records){
                buffer.add(record);
            }

            if (buffer.size()>=minBatchSize){
                //todo 入库操作等
                consumer.commitSync();  //变更为手动提交,就是声明了准确的时机何时被消费掉,如果变为自动提交,如果在入库等操作完成之前宕掉,consumer重新恢复后,存在消息丢失;
                                        //这里会存在问题,在进行入库等操作后,但是未提交offset时,突然宕机,等消费者恢复后,会重复消费,保证at-least-once
                buffer.clear();
            }
        }
    }

    // commitSync同时显式指定offset
    public static void consumerWithManualOffsetExplict(KafkaConsumer consumer,String topic) {
        consumer.subscribe(Collections.singletonList(topic));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                //消费的records的所在partition
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                    for (ConsumerRecord<String, String> record : partitionRecords) {
                        System.out.println(record.offset() + ": " + record.value());
                    }
                    //当次消费中,当前分区最大record的offset
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
                }
            }
        }finally {
            consumer.close();
        }
    }








}
