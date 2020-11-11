package com.lingzhan.kafka;


import kafka.Kafka;
import kafka.server.KafkaServer;
import kafka.zk.KafkaZkClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.MetadataUpdater;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 * KafkaProducer 是线程安全的
 * 线程间共享一个单独的producer比多个producer要更快
 *
 *
 * Created by 凌战 on 2019/12/11
 */
@Slf4j
public class ProducerDemo {

    static private final String TOPIC = "testKafka";

    static private final String ZOOKEEPER = "node1:2181/kafka2.2.1";

    static private final String BROKER_LIST = "node1:9092";
    static private final String EXTERNAL_BROKER_LIST ="192.168.11.10:9093";


    public static void main(String[] args) throws InterruptedException {

        KafkaProducer<String, String> producer = initProducer();
        //sendMessage(producer);
        sendMessageSync(producer);


    }


    private static KafkaProducer<String, String> initProducer() {

        //System.setProperty("java.security.auth.login.config", "C:\\resources\\workspace\\learning_project\\study\\src\\main\\resources\\kafka_admin_jass.conf");

        Properties props = new Properties();

        //必备属性
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, EXTERNAL_BROKER_LIST);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        //非必备属性
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class);
        //props.put("producer.type", "async");
        props.put("batch.num.messages", "3");
        props.put("queue.buffer.max.ms", "10000000");
        props.put("queue.buffering.max.messages", "1000000");
        props.put("queue.enqueue.timeout.ms", "20000000");
        props.put(ProducerConfig.LINGER_MS_CONFIG,"2");

        props.put("sasl.jaas.config","org.apache.kafka.common.security.scram.ScramLoginModule required username=\"lily\" password=\"lily123456\";");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "SCRAM-SHA-256");




        return new KafkaProducer<String, String>(props);
    }


    //FireAndForget
    //todo 发现没有数据生产到kafka中, producer.send()是有延迟的
    //解决方案1：send之后调用flush,该方法会将数据全部生产到kafka中,否则会阻塞,影响性能
    //解决方案2：对性能要求较高,推荐将 linger.ms 参数设置一个比 0 大的值（默认是 0）batch.size 也可以设置一下（默认是16384）,带一个callBack
    private static void sendMessage(KafkaProducer<String, String> producer) throws InterruptedException {


        for (int i=1;;i++) {
            ProducerRecord<String, String> message1 = new ProducerRecord<>(TOPIC, String.valueOf(i), "test"+i);
            producer.send(message1);
//            ProducerRecord<String, String> message2 = new ProducerRecord<>(TOPIC, "12", "test13");
//            producer.send(message2);
//            ProducerRecord<String, String> message3 = new ProducerRecord<>(TOPIC, "11", "test12");
//            producer.send(message3);
        }
        //Thread.sleep(10000);
        //producer.flush();

    }


    //Synchronous
    private static void sendMessageSync(KafkaProducer<String, String> producer) {

        ProducerRecord<String, String> message1 = new ProducerRecord<>(TOPIC, "11", "test11");
        Future<RecordMetadata> send=producer.send(message1);

        RecordMetadata recordMetadata=null;
        try{
            recordMetadata=send.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.printf("partition：%s,offset：%d",recordMetadata.partition(),recordMetadata.offset());


    }



}
