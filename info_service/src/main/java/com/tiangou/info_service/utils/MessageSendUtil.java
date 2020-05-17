package com.tiangou.info_service.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 发送消息的工具
 * Created by 凌战 on 2019/2/15
 */
public class MessageSendUtil {

    public static void  sendMessage(String address,String message){
        try {
            URL url=new URL(address);
            //连接目标url (模仿用户访问网站行为)
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setAllowUserInteraction(true);
            conn.setUseCaches(false);
            conn.setReadTimeout(6*1000);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            conn.setRequestProperty("Content-Type","application/json");
            conn.connect();

            //输出流，模拟数据传输过去，网站从而采集数据
            OutputStream out=conn.getOutputStream();
            BufferedOutputStream outputStream=new BufferedOutputStream(out);
            outputStream.write(message.getBytes());
            outputStream.flush();

            //访问url,创建输入流,读取数据 (即模仿网站返回给客户端数据)
            InputStream in=conn.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String str;
            StringBuilder stringBuilder=new StringBuilder();
            while ((str=reader.readLine())!=null){
                stringBuilder.append(str);
            }
            reader.close();
            String res=stringBuilder.toString();

            System.out.println(conn.getResponseCode());
            System.out.println(res);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






}
