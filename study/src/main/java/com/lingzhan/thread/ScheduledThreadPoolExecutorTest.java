package com.lingzhan.thread;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 凌战 on 2020/8/28
 */
public class ScheduledThreadPoolExecutorTest {

  public static void main(String[] args) {

    ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(4);
    TestThread thread=new TestThread();
    executor.scheduleAtFixedRate(thread,1,1, TimeUnit.SECONDS);
    executor.scheduleAtFixedRate(thread,1,1, TimeUnit.SECONDS);



  }

}
