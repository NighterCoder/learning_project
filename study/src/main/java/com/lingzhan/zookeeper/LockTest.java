package com.lingzhan.zookeeper;

/**
 * Created by 凌战 on 2020/2/23
 */
public class LockTest {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DistributedLock distributedLock=new DistributedLock(
                            "localhost:8081",
                            3000,
                            "/lock"
                    );

                    try{
                        distributedLock.lock();
                        System.out.println(Thread.currentThread().getName()+"开始执行!");
                        Thread.sleep(100);


                        System.out.println(Thread.currentThread().getName()+"执行完毕!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        distributedLock.unlock();
                    }



                }
            }).start();
        }

    }
}
