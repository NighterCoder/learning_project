package com.lingzhan.thread;

/**
 * Created by 凌战 on 2020/8/28
 */
public class TestThread implements Runnable{
  @Override
  public void run() {
    Thread t=Thread.currentThread();
    System.out.println(t.getName());
  }
}
