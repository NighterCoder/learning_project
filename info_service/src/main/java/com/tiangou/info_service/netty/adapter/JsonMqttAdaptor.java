package com.tiangou.info_service.netty.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tiangou.info_service.netty.AdaptorException;
import com.tiangou.info_service.netty.basic.session.SessionMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 处理Mqtt消息的Adaptor,将二进制转变
 *
 * Created by 凌战 on 2020/8/26
 */
public class JsonMqttAdaptor {

   private static final Gson GSON = new Gson();
   private static final Charset UTF8=Charset.forName("UTF-8");
   private static final ByteBufAllocator ALLOCATOR=new UnpooledByteBufAllocator(false);

   public static void convertToMsg(SessionMsgType type, MqttMessage inbound){
      switch (type){
         case POST_TELEMETRY_REQUEST:


      }
   }


  /**
   * 处理获取属性的请求
   * @param inbound
   */
   private static void convertToGetAttributesRequest(MqttPublishMessage inbound) throws
       AdaptorException {
      try {
         String payload = validatePayload(inbound.payload());
         JsonElement requestBody = new JsonParser().parse(payload);
         Set<String> clientKeys = toStringSet(requestBody, "clientKeys");
         Set<String> sharedKeys = toStringSet(requestBody, "sharedKeys");
         if (clientKeys == null && sharedKeys == null) {
         } else {
            for (String clientKey : clientKeys) {
               System.out.print("客户端属性:" + clientKey +" ");
            }
            for (String sharedKey : sharedKeys) {
               System.out.print("共享设备属性:" + sharedKey + " ");
            }
         }
      }catch (RuntimeException e) {
         throw new AdaptorException(e);
      }
   }


   private static void convertToTelemetryUploadRequest(MqttPublishMessage inbound)
       throws AdaptorException {
     String payload=validatePayload(inbound.payload());

     //JsonConverter





   }





   private static Set<String> toStringSet(JsonElement requestBody,String name){
      JsonElement element=requestBody.getAsJsonObject().get(name);
      if (element != null) {
         return new HashSet<>(Arrays.asList(element.getAsString().split(",")));
      } else {
         return null;
      }
   }


   public static String validatePayload(ByteBuf payloadData) throws AdaptorException {
      try{
         String payload=payloadData.toString(UTF8);
         if (payload == null) {
            throw new AdaptorException(new IllegalArgumentException("Payload is empty!"));
         }
         return payload;
      }finally {
         payloadData.release();
      }
   }





}
