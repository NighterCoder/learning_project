package com.lingzhan.java_basic.lambda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * Created by 凌战 on 2020/5/9
 */
public class CompletableFutureTest {
  public static void main(String[] args) {

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    CompletableFuture.runAsync(() -> {
      System.out.println("当前时间为：" + df.format(new Date()));

      //throw new ArithmeticException("illegal exception!");
    }).exceptionally(e -> {
      System.out.println("异常为： "+e.getMessage());
      return null;
    }).whenComplete((v, e) -> System.out.println("complete")).join();


    int f = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "first";
    }).thenCompose(str -> CompletableFuture.supplyAsync(() -> {
      String str2 = "second";
      return str.length() + str2.length();
    })).join();
    System.out.println("字符串长度为：" + f);





  }

}
