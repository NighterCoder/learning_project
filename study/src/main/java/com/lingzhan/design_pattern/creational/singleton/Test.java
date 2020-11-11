package com.lingzhan.design_pattern.creational.singleton;

/**
 * Created by 凌战 on 2020/8/27
 */
public class Test {
  public static void main(String[] args) {

    //复现懒汉式问题
    Thread t1=new Thread(new T());
    Thread t2=new Thread(new T());
    t1.start();
    t2.start();
    System.out.println("program end");


  }
}
