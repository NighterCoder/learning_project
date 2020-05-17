package com.tiangou.info_service.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 凌战 on 2019/1/6
 */

@RestController
@RequestMapping("/kafka")
public class CollectController {


    protected final Logger logger= LoggerFactory.getLogger(CollectController.class);

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate kafkaTemplate;

    @RequestMapping(value = "/send",method = RequestMethod.GET)
    public void sendKafka(HttpServletRequest request, HttpServletResponse response){

        try {
            String message=request.getParameter("message");
            logger.info("kafka的消息={}",message);
            kafkaTemplate.send("testflink","key",message);
            logger.info("发送kafka成功.");
        }catch (Exception e){
            logger.info("发送至kafka失败");
        }


    }




}
