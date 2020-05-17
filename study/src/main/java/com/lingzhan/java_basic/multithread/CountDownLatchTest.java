package com.lingzhan.java_basic.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 凌战 on 2019/12/30
 */
public class CountDownLatchTest {

    private static class ProcessJob implements Runnable {
        @Override
        public void run() {
            System.out.println("Some Job");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool(); //可缓存
        int jobSize = 20;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch stopLatch = new CountDownLatch(jobSize);

        for (int i = 0; i < jobSize; i++) {
            executorService.submit(() -> {

                try {
                    // 线程都进入等待状态
                    startLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    new ProcessJob().run();
                } finally {
                    // 执行完一个线程执行countDown
                    stopLatch.countDown();
                }

            });
        }

        long start = System.currentTimeMillis();
        // 所有线程开始执行
        startLatch.countDown();

        //等待所有线程执行完任务,即stopLatch减到1
        stopLatch.await();

        long end = System.currentTimeMillis();

        System.out.println("Total cost time：" + (end - start));
        executorService.shutdown();


    }
}
