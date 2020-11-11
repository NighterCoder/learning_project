package com.tiangou.info_service.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;

/**
 * Created by 凌战 on 2020/8/25
 */
public class IOTMqttServer {

  private static final int PORT= 1883;
  private static final String leakDetectorLevel = "DISABLED";
  private static final Integer bossGroupThreadCount = 1;
  private static final Integer workerGroupThreadCount = 8;
  private static final Integer maxPayloadSize = 65536;

  public static void main(String[] args) {
    ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(leakDetectorLevel));

    // boss线程组
    EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupThreadCount);
    // worker线程组
    EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupThreadCount);

/*    try{
      ServerBootstrap b =new ServerBootstrap();
     *//* b.group(bossGroup,workerGroup)
          .channel(NioServerSocketChannel.class)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler()*//*

    }*/




  }



}
