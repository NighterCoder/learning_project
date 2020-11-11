package com.lingzhan.nifi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 凌战 on 2020/11/3
 */
public class Main {
  public static void main(String[] args) {
    Calendar now = Calendar.getInstance();

    now.get(Calendar.YEAR);

    System.out.println("年: " + now.get(Calendar.YEAR));

    System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");

    System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));

    System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));

    System.out.println("分: " + now.get(Calendar.MINUTE));

    System.out.println("秒: " + now.get(Calendar.SECOND));



    System.out.println("当前时间毫秒数：" + now.getTimeInMillis());


    Date date = new Date();

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");


    String str = df.format(date);

    System.out.println(str);

  }

}
