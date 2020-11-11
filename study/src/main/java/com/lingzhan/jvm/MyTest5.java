package com.lingzhan.jvm;

import java.util.Random;

/**
 * 当一个接口在初始化时,并不要求其父接口都完成了初始化
 *
 * 只有在真正使用到父接口的时候(如引用父接口中所定义的常量时),才会初始化
 *
 * Created by 凌战 on 2020/5/17
 */
public class MyTest5 {

  public static void main(String[] args) {

    // 调用b1时,如果没有MyParent5就会报错,b1是运行期才会产生
    System.out.println(MyChild5.b1);

  }
}


interface MyParent5{

  public static final int a=5;

}

interface MyChild5 extends MyParent5{
  public static final int b=6;

  // b1变量是基于new Random(),在运行期才会产生
  public static final int b1=new Random().nextInt(2);



}