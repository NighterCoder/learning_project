package com.tiangou.info_service.web;

import com.alibaba.fastjson.JSONObject;
import com.tiangou.info_service.entity.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 数据收集接口并将数据处理后发送到Kafka
 * Created by 凌战 on 2019/1/4
 */
@Controller
@RequestMapping("/infoSearch")
public class DsInfoSearchService {


    @Autowired
    private KafkaTemplate kafkaTemplate;

    //将接收到的数据发送到Kafka中
    @RequestMapping(value = "/webInfo",method = RequestMethod.POST)
    public void webInfoSearch(@RequestBody String jsonStr, HttpServletRequest request, HttpServletResponse response){
        System.out.println("===未转化的原始json串来了==="+jsonStr);
        //定义传入到Kafka中的KafkaMessage
        KafkaMessage kafkaMessage=new KafkaMessage();
        kafkaMessage.setJsonMessage(jsonStr);
        kafkaMessage.setCount(1);
        kafkaMessage.setTimestamp(new Date().getTime());
        //再将kafkaMessage对象实例转换成json串
        String kafkaMessageJsonStr= JSONObject.toJSONString(kafkaMessage);
        System.out.println("===转为json串的kafkaMessage==="+kafkaMessageJsonStr);
        //业务开始
        kafkaTemplate.send("flinkTest1",kafkaMessageJsonStr);
        //业务结束
        PrintWriter printWriter=getWriter(response);
        response.setStatus(HttpStatus.OK.value());
        printWriter.write("Success"+"\n");

        closePrintWriter(printWriter);


    }


    private PrintWriter getWriter(HttpServletResponse response){
        //设置编码格式
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        OutputStream out= null;
        PrintWriter printWriter=null;
        try {
            out = response.getOutputStream();
            printWriter=new PrintWriter(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return printWriter;
    }

    private void closePrintWriter(PrintWriter printWriter){
        printWriter.flush();
        printWriter.close();
    }


}
