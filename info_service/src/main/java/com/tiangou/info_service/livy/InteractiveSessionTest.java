package com.tiangou.info_service.livy;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 凌战 on 2020/6/17
 */
public class InteractiveSessionTest {
  public static void main(String[] args) {

    RestTemplate restTemplate=new RestTemplate();

/*    ResponseEntity<String> response=restTemplate.getForEntity("http://hnode10:7998/sessions",String.class);
    int code1=response.getStatusCodeValue();
    if (code1== HttpStatus.OK.value()){
      JSONObject jsonObject=JSONObject.parseObject(response.getBody());
      System.out.println(jsonObject.get("id"));
    }*/


    HttpHeaders headers=new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    JSONObject object=new JSONObject();
    object.put("kind","sql");
    object.put("proxyUser","hdfs");
    object.put("driverMemory","3G");
    object.put("driverCores",2);
    object.put("executorMemory","3G");
    object.put("executorCores",2);
    object.put("numExecutors",3);
    Map<String,String> conf=new HashMap<>();
    conf.put("spark.files","/user/collie/hive/hive-site.xml");
    object.put("conf",conf);
    HttpEntity<Object> entity=new HttpEntity<>(object,headers);
    ResponseEntity<String> postResponse=restTemplate.postForEntity("http://hnode10:7998/sessions",entity,String.class);
    int code2 = postResponse.getStatusCodeValue();
    if (code2 == HttpStatus.OK.value() || code2 == HttpStatus.CREATED.value()) {
      JSONObject jsonObject1=JSONObject.parseObject(postResponse.getBody());
      System.out.println(jsonObject1.get("id"));
    }




    //System.out.println(response.getStatusCode());


  }
}
