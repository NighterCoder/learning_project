package com.lingzhan.design_pattern.creational.singleton;

/**
 * Created by 凌战 on 2020/8/27
 */
public class T implements Runnable {
  @Override
  public void run() {
    LazySingleton lazySingleton=LazySingleton.getInstance();
    System.out.println(Thread.currentThread().getName()+"  "+lazySingleton);

  }
}
