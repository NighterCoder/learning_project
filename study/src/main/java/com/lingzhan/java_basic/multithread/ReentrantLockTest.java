package com.lingzhan.java_basic.multithread;

import org.springframework.jca.cci.core.InteractionCallback;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 凌战 on 2019/12/29
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock=new ReentrantLock();

        // 声明一个条件锁
        Condition condition=lock.newCondition();

        new Thread(()->{
            try {
                lock.lock(); //1 先加锁

                try {
                    System.out.println("before await");
                    // 3 等待条件
                    condition.await();
                    System.out.println("after await");
                }finally {
                    lock.unlock();
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();


        Thread.sleep(1000);
        lock.lock();
        try{
            Thread.sleep(2000);
            System.out.println("before signal");
            condition.signal();
            System.out.println("after signal");
        }finally {
            lock.unlock();
        }



    }
}
