package com.tiangou.info_service.netty;

import static com.tiangou.info_service.netty.MqttTopics.DEVICE_ATTRIBUTES_TOPIC;
import static com.tiangou.info_service.netty.basic.session.SessionMsgType.GET_ATTRIBUTES_REQUEST;
import static com.tiangou.info_service.netty.basic.session.SessionMsgType.POST_ATTRIBUTES_REQUEST;

import com.tiangou.info_service.netty.adapter.JsonMqttAdaptor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * Created by 凌战 on 2020/8/25
 */
public class MqttTransportHandler extends ChannelInboundHandlerAdapter implements
    GenericFutureListener<Future<? super Void>> {

  private volatile boolean connected;
  private volatile InetSocketAddress address;
  //qos:消息到达次数
  private final ConcurrentMap<MqttTopicMatcher,Integer> mqttQoSMap;


  public MqttTransportHandler(){
    this.mqttQoSMap=new ConcurrentHashMap<>();
  }

  /**
   * 接收对应消息
   * @param ctx 上下文
   * @param msg 消息
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    if(msg instanceof MqttMessage){
      processMqttMsg(ctx,(MqttMessage)msg);
    }else {
      ctx.close();
    }
  }

  private void processMqttMsg(ChannelHandlerContext ctx, MqttMessage msg) {
    address=(InetSocketAddress) ctx.channel().remoteAddress();
    if (msg.fixedHeader() == null){
      processDisconnect(ctx);
      return;
    }

    switch(msg.fixedHeader().messageType()){
      case CONNECT:

    }


  }



  private void processPublish(ChannelHandlerContext ctx, MqttPublishMessage mqttMsg){

  }


  /**
   * 对消息的主题进行判断,并对主体内的内容进行物理模型的解析
   *
   * @param ctx
   * @param mqttMsg
   * @param topicName
   * @param msgId
   */
  private void processDevicePublish(ChannelHandlerContext ctx,MqttPublishMessage mqttMsg,String topicName,int msgId){

    try {
      // 发布消息
      if (topicName.equals(MqttTopics.DEVICE_TELEMETRY_TOPIC)) {
        System.out.println(JsonMqttAdaptor.validatePayload(mqttMsg.payload()));
      } else if(topicName.equals(DEVICE_ATTRIBUTES_TOPIC)) {
        // 更改属性消息
        JsonMqttAdaptor.convertToMsg(POST_ATTRIBUTES_REQUEST, mqttMsg);
      } else if(topicName.equals(MqttTopics.DEVICE_ATTRIBUTES_REQUEST_TOPIC_PREFIX)) {
        // 获取属性消息
        JsonMqttAdaptor.convertToMsg(GET_ATTRIBUTES_REQUEST, mqttMsg);
      }
      //ctx.writeAndFlush(createMqttPubAckMsg(msgId));
    } catch (AdaptorException e) {
      ctx.close();
    }



  }




  /**
   * 判断是否还在连接中
   * @param ctx
   */
  private boolean checkConnected(ChannelHandlerContext ctx){
    if(connected){
      return true;
    }else{
      ctx.close();
      return false;
    }
  }


  private void processDisconnect(ChannelHandlerContext ctx) {
    ctx.close();
  }

  @Override
  public void operationComplete(Future<? super Void> future) throws Exception {

  }


}
