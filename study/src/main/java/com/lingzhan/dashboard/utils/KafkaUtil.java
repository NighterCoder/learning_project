package com.lingzhan.dashboard.utils;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *  addSource参数为FlinkKafkaConsumer,
 *  因此返回父类FlinkKafkaConsumerBase
 *  Created by 凌战 on 2019/11/12
 */
public class KafkaUtil {

    public static FlinkKafkaConsumerBase<String> text(String topic) throws IOException {
        return text(topic, "kafka.properties");
    }

    /**
     * @param topic 主题名称
     * @param configPath Kafka属性配置文件路径
     */
    public static FlinkKafkaConsumerBase<String> text(String topic,String configPath) throws IOException {

        //1.加载Kafka属性
        Properties prop=new Properties();
        //Class.getClassLoader.getResourceAsStream 默认是从ClassPath根下获取,path不能以“/"开头
        InputStream in=KafkaUtil.class.getClassLoader().getResourceAsStream(configPath);
        prop.load(in);

        //2.构造FlinkKafkaConsumer
        FlinkKafkaConsumerBase<String> consumer=new FlinkKafkaConsumer011<>(topic,new SimpleStringSchema(),prop);
        //todo 可以进行消费者的相关配置
        // 本地debug不提交offset consumer.setCommitOffsetsOnCheckpoints(false);
        return consumer;
    }


}
