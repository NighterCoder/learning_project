package com.lingzhan.nifi;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import java.util.Map;

/**
 * 开始标识:
 * 0xAA=-86
 * 0xAB=-85
 * 0xAC=-84
 *
 *
 * 结束标识:
 * 0xBA=-70
 * 0xBB=-69
 * 0xBC=-68
 *
 *
 *
 * Created by 凌战 on 2020/10/30
 */
public class TestNifi {


  private final ByteArrayOutputStream currBytes = new ByteArrayOutputStream(4096);

  public static void main(String[] args) {

//    byte[] testBytes={0,1,0,1,0,1,0,1};
//
//    System.out.println(bytesToHex(testBytes));

    //System.out.println(hexStr2Bytes("AA"));
    // BABBBC
    System.out.println((byte)0x53);





 /*   final ByteArrayOutputStream currBytes = new ByteArrayOutputStream(4096);
    currBytes.write((byte)0xBB);
    System.out.println(currBytes.size());
    currBytes.reset();
    System.out.println(currBytes.size());*/

  }





  protected void processBuffer(final SocketChannel socketChannel, final ByteBuffer socketBuffer) throws InterruptedException,
      IOException {
    // get total bytes in buffer
    final int total = socketBuffer.remaining();
    final InetAddress sender = socketChannel.socket().getInetAddress();

    // go through the buffer looking for the end of each message
    currBytes.reset();
    for (int i = 0; i < total; i++) {
      // NOTE: For higher throughput, the looking for \n and copying into the byte stream could be improved
      // Pull data out of buffer and cram into byte array
      byte currByte = socketBuffer.get();

      //if ()



      // check if at end of a message

      /*if (currByte == getDelimiter()) {
        if (currBytes.size() > 0) {
          final SocketChannelResponder response = new SocketChannelResponder(socketChannel);
          final Map<String, String> metadata = EventFactoryUtil.createMapWithSender(sender.toString());
          final E event = eventFactory.create(currBytes.toByteArray(), metadata, response);
          events.offer(event);
          currBytes.reset();

          // Mark this as the start of the next message
          socketBuffer.mark();
        }
      } else {
        currBytes.write(currByte);
      }*/



    }
  }


  protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

  public static String bytesToHex(byte[] bytes)
  {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++)
    {
      int v = bytes[j] & 0xFF;
      hexChars[(j * 2)] = hexArray[(v >>> 4)];
      hexChars[(j * 2 + 1)] = hexArray[(v & 0xF)];
    }
    return new String(hexChars);
  }

  private static byte[] hexStr2Bytes(String src){
    /*对输入值进行规范化整理*/
    src = src.trim().replace(" ", "").toUpperCase(Locale.US);
    //处理值初始化
    int m=0,n=0;
    int iLen=src.length()/2; //计算长度
    byte[] ret = new byte[iLen]; //分配存储空间

    for (int i = 0; i < iLen; i++){
      m=i*2+1;
      n=m+1;
      ret[i] = (byte)(Integer.decode("0x"+ src.substring(i*2, m) + src.substring(m,n)) & 0xFF);
    }
    return ret;
  }

}
