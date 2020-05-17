package com.lingzhan.java_basic.multithread;

/**
 * Created by 凌战 on 2020/2/23
 */
public class ThreadLocalTest {

    static ThreadLocal<String> localVal=new ThreadLocal<>();

    static void print(String str){
        // 打印当前线程中本地内存中本地变量的值
        System.out.println(str + " :"+localVal.get());
        // 清除本地内存中的本地变量
        localVal.remove();
    }

    public static void main(String[] args) {

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {

                localVal.set("localVar1");
                print("thread1");
                System.out.println("after remove : "+localVal.get());


            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {

                localVal.set("localVar2");
                print("thread2");
                System.out.println("after remove : "+localVal.get());


            }
        });

        t1.start();
        t2.start();
    }

}
