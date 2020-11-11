package com.tiangou.info_service.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;


/**
 * Created by 凌战 on 2020/8/25
 */
public class MqttTransportServerInitializer extends ChannelInitializer<SocketChannel> {

  private final int maxPayloadSize;

  public MqttTransportServerInitializer(int maxPayloadSize){
    this.maxPayloadSize=maxPayloadSize;
  }

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    ChannelPipeline pipeline=socketChannel.pipeline();
    // 添加mqtt解码器
    pipeline.addLast("decoder",new MqttDecoder(maxPayloadSize));
    // 添加mqtt编码器
    pipeline.addLast("encoder", MqttEncoder.INSTANCE);




  }
}
