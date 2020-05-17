package com.lingzhan.java_basic.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by 凌战 on 2020/1/8
 */
public class NioTest {
    public static void main(String[] args) throws IOException {

        RandomAccessFile aFile=new RandomAccessFile("C:\\resources\\workspace\\tiangou\\flink_analysis\\src\\main\\resources\\data\\nio-data.txt","rw");
        // 建立文件通道
        FileChannel inChannel=aFile.getChannel();
        //
        ByteBuffer buf=ByteBuffer.allocate(48);

        int bytesRead=inChannel.read(buf);
        while (bytesRead!=-1){
            System.out.println("Read:"+bytesRead);
            // 首先读取数据到Buffer
            buf.flip();
            // 然后反转Buffer,接着从Buffer中读取数据
            while (buf.hasRemaining()){
                System.out.println((char)buf.get());
            }

            buf.clear();
            bytesRead=inChannel.read(buf);
        }
        aFile.close();


    }
}
