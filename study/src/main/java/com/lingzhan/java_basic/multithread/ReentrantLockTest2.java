package com.lingzhan.java_basic.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 凌战 on 2019/12/30
 */
public class ReentrantLockTest2 {

    ReentrantLock reentrantLock=new ReentrantLock();

    // 生产者条件
    Condition producerCondition=reentrantLock.newCondition();
    // 消费者条件
    Condition consumerCondition=reentrantLock.newCondition();

    private int count=0;

    private volatile boolean isHaveChicken=false;

    public void produceChicken(){

        reentrantLock.lock();
        while (isHaveChicken){

            try {
                System.out.println("有烤鸡了" + Thread.currentThread().getName() + "不生产了");
                //等待条件,释放所有锁并进入条件队列
                //生产者进入等待状态
                producerCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        count++;
        System.out.println(Thread.currentThread().getName() + "产生了第" + count + "个烤鸡，赶紧开始卖");
        isHaveChicken=true;
        //提示消费者条件已完成
        consumerCondition.signal();
        reentrantLock.unlock();

    }

    public void sellChicken(){
        reentrantLock.lock();
        while (!isHaveChicken){
            try {
                System.out.println("没有烤鸡了" + Thread.currentThread().getName() + "不卖了");
                // 消费线程进入等待状态
                consumerCondition.await();
            } catch (Exception e) {
                System.out.println("error" + e.getMessage());
            }

        }

        count--;
        isHaveChicken=false;
        System.out.println(Thread.currentThread().getName() + "卖掉了第" + count + 1 + "个烤鸡，赶紧开始生产");
        producerCondition.signal();
        reentrantLock.unlock();


    }


    public static void main(String[] args) {

        ReentrantLockTest2 lock=new ReentrantLockTest2();
        new Thread(()->{
            Thread.currentThread().setName("生产者1号");
            while (true){
                lock.produceChicken();
            }
        }).start();

        new Thread(()->{
           Thread.currentThread().setName("生产者2号");
            while (true){
                lock.produceChicken();
            }

        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("消费者1号");
            while (true) {
                lock.sellChicken();
            }
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("消费者2号");
            while (true) {
                lock.sellChicken();
            }
        }).start();






    }

}
