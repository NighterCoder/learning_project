package com.lingzhan.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import redis.clients.jedis.Jedis;

/**
 * 基于Redis实现的锁
 * Created by 凌战 on 2020/4/28
 */
public class RedisLock {


  private static Integer inventory=1001;
  private static final int NUM=1000;
  private static LinkedBlockingQueue linkedBlockingQueue=new LinkedBlockingQueue();
  static ReentrantLock reentrantLock=new ReentrantLock();


  public static void main(String[] args) throws InterruptedException {

    //自定义线程池
    //1.核心线程数 2.最大线程数 3.当前线程数超过核心线程数,空闲线程的存活时间 4.时间单位
    //5.当前线程数量大于核心线程数量,放入到阻塞队列中
    ThreadPoolExecutor threadPoolExecutor=
        new ThreadPoolExecutor(inventory,inventory,10L, TimeUnit.SECONDS,linkedBlockingQueue);

    final CountDownLatch countDownLatch=new CountDownLatch(NUM);
    long start=System.currentTimeMillis();
    for (int i=0;i<=NUM;i++){
      threadPoolExecutor.execute(new Runnable() {
        @Override
        public void run() {
          reentrantLock.lock();
          inventory--;
          reentrantLock.unlock();
          System.out.println("线程执行:"+Thread.currentThread().getName());
          countDownLatch.countDown();

        }
      });
    }
    threadPoolExecutor.shutdown();
    // 只有countDownLatch变为0,才会继续执行,否则阻塞
    countDownLatch.await();
    long end=System.currentTimeMillis();
    System.out.println("执行线程数:"+NUM+"  总耗时:"+(end-start)+"  库存数为:"+inventory);




  }

}
