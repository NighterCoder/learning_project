package com.tiangou.info_service.entity;

import lombok.Data;

/**
 * 传入到Kafka中的Message格式
 * Created by 凌战 on 2019/2/15
 */
@Data
public class KafkaMessage {

    private String jsonMessage;
    private Integer count;
    private Long timestamp;

}
