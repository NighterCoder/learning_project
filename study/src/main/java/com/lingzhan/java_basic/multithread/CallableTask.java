package com.lingzhan.java_basic.multithread;

import java.util.concurrent.*;

/**
 * Created by 凌战 on 2019/12/9
 */
public class CallableTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final Integer[] result={null};

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                result[0]=1111;
            }
        };

        //定义一个线程池
        ExecutorService executorService= Executors.newCachedThreadPool();

        //1.Executors.callable(runnable) 转换成Callable<Object>,Future返回null
        Callable<Object> callable=Executors.callable(runnable);
        Future future=executorService.submit(callable);
        //输出运行结果,肯定是null
        System.out.println("Executors.callable(runnable) 的future = " + future.get());

        //2.Executors.callable(runnable,result) 转换成Callable<V>,future有值
        Callable<Integer> callable1=Executors.callable(runnable,result[0]);
        Future future1=executorService.submit(callable1);
        System.out.println("Executors.callable(runnable, result) 的future = " + future1.get());

    }

}
