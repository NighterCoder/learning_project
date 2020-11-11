package com.lingzhan.nifi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 * Created by 凌战 on 2020/10/31
 */
public class TestTcp {

  private static final ByteArrayOutputStream currBytes = new ByteArrayOutputStream(4096);

  public static void main(String[] args) throws IOException, InterruptedException {

    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.socket().bind(new InetSocketAddress(9999));

    serverSocketChannel.configureBlocking(false);
    ByteBuffer socketBuffer = ByteBuffer.allocate(48000000);

    while (true) {
      SocketChannel socketChannel = serverSocketChannel.accept();
      if (socketChannel!=null) {
        while (socketChannel.read(socketBuffer) > 0) {
          // prepare byte buffer for reading
          socketBuffer.flip();
          // mark the current position as start, in case of partial message read
          socketBuffer.mark();
          // process the contents that have been read into the buffer
          processBuffer(socketChannel, socketBuffer);

          // Preserve bytes in buffer for next call to run
          // NOTE: This code could benefit from the  two ByteBuffer read calls to avoid
          // this compact for higher throughput
          socketBuffer.reset();
          socketBuffer.compact();
        }
      }

    }


  }

  protected static void processBuffer(final SocketChannel socketChannel, final ByteBuffer socketBuffer) throws InterruptedException, IOException {
    // get total bytes in buffer
    final int total = socketBuffer.remaining();
    final InetAddress sender = socketChannel.socket().getInetAddress();

    // go through the buffer looking for the end of each message
    currBytes.reset();

    for (int i = 0; i < total - 2; i++) {
      //for (int i = socketBuffer.position(); i < total; ++i) {
      //for (int i = 0; i < total; i++) {
      // NOTE: For higher throughput, the looking for \n and copying into the byte stream could be improved
      // Pull data out of buffer and cram into byte array
      byte b0 = socketBuffer.get(i);
      byte b1 = socketBuffer.get(i + 1);
      byte b2 = socketBuffer.get(i + 2);
      // check if at end of a message
      //if (currByte == getDelimiter()) {
      // todo 修改
      // 遇到AAABAC则写入
      if (b0 == -86 && b1 == -85 && b2 == -84) {
        // 正常结束,第一次遇到过AAABAC,currBytes大小为0
        if (currBytes.size()==0) {
          currBytes.write(b0);
          currBytes.write(b1);
          currBytes.write(b2);
          i=i+2;
        }else{
          // 包还没有结束,但是又遇到开始AAABAC
          // 之前的丢弃
          currBytes.reset();
          currBytes.write(b0);
          currBytes.write(b1);
          currBytes.write(b2);
          i=i+2;
        }
      }else {


        if (b0 == -70 && b1 == -69 && b2 == -68) {
          if (currBytes.size() > 0) {
            currBytes.write(b0);
            currBytes.write(b1);
            currBytes.write(b2);
            //String res = bytesToHex(this.currBytes.toByteArray());
            //byte[] b = hexStr2Bytes(res);
          /*SocketChannelResponder response = new SocketChannelResponder(socketChannel);
          Map<String, String> metadata = EventFactoryUtil.createMapWithSender(sender.toString());
          E event = this.eventFactory.create(this.currBytes.toByteArray(), metadata, response);
          //E event = this.eventFactory.create(b, metadata, response);
          //E event = this.eventFactory.create(res.getBytes("UTF-8"), metadata, response);
          this.events.offer(event);*/

            System.out.println(currBytes.toString());

            currBytes.reset();

            //socketBuffer.position(i + 3);
            socketBuffer.mark();
          }
        } else {
          if (currBytes.size() > 0) {
            currBytes.write(b0);
          }
        }
      }

    }
  }

}
