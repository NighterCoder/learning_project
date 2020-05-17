package com.lingzhan.java_basic.multithread;

/**
 * Created by 凌战 on 2019/12/27
 */
public class JoinTest {
    public static void main(String[] args) {
        method1();
        method2();
    }


    private static void method1() {
        Thread thread1 = new Thread(() -> System.out.println("thread1 is finished"));

        Thread thread2 = new Thread(() -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("thread2 is finished");
        });

        Thread thread3 = new Thread(() -> {
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("thread3 is finished");
        });

        thread3.start();
        thread1.start();
        thread2.start();

    }


    private static void method2() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "执行完毕!");
            }
        };


        Thread thread1 = new Thread(runnable, "thread1");
        Thread thread2 = new Thread(runnable, "thread2");
        Thread thread3 = new Thread(runnable, "thread3");

        try {
            thread1.start();
            thread1.join();
            thread2.start();
            thread2.join();
            thread3.start();
            thread3.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
