package com.lingzhan.java_basic.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 凌战 on 2020/1/3
 */
public class UseSingleConditionWaitNotify {

    public static void main(String[] args) throws InterruptedException {
        MyService service=new MyService();

        ThreadA a=new ThreadA(service);
        a.start(); // 调用MyService的await方法：输出1;遇到条件,线程进入等待;等待3秒,执行signal方法,输出2;条件等到,输出3;等待3秒,输出4

        Thread.sleep(3000);
        System.out.println("主线程等待3秒");

        service.signal();
    }


    static public class MyService {

        private Lock lock = new ReentrantLock();
        public Condition condition = lock.newCondition();

        public void await() {
            lock.lock();
            try {
                System.out.println("await时间为" + System.currentTimeMillis());
                // 要想调用这些方法,需要提前获取到Condition对象关联的锁
                // 当调用await方法,当前线程会释放锁并在此等待
                // 其他线程调用Condition对象的signal()方法,通知当前线程,当前线程才从await()方法返回,并且在返回前已经获取了锁
                condition.await();
                System.out.println("这是condition.await()方法之后的语句,condition.signal()方法之后我才被执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void signal() {
            lock.lock();
            try {
                System.out.println("signal时间为" + System.currentTimeMillis());
                condition.signal();
                Thread.sleep(9000);
                System.out.println("这是condition.signal()方法之后的语句");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


    }

    static public class ThreadA extends Thread {

        private MyService service;

        public ThreadA(MyService service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.await();
        }
    }


}
