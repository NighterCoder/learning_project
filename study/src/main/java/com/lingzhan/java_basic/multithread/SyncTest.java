package com.lingzhan.java_basic.multithread;

/**
 * Created by 凌战 on 2019/12/27
 */
public class SyncTest {

    private static void sync(String tips){
        synchronized (SyncTest.class){
            System.out.println(tips);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->sync("thread1")).start();
        Thread.sleep(100);
        new Thread(()->sync("thread2")).start();
        Thread.sleep(100);
        new Thread(()->sync("thread3")).start();
        Thread.sleep(100);
        new Thread(()->sync("thread4")).start();
        Thread.sleep(100);
    }

}
