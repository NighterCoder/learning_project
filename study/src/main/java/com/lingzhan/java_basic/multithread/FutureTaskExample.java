package com.lingzhan.java_basic.multithread;

import java.util.concurrent.*;

/**
 * Created by 凌战 on 2019/12/9
 */
public class FutureTaskExample {

    public static void main(String[] args) {

        MyCallable callable1 = new MyCallable(1000);
        MyCallable callable2 = new MyCallable(2000);

        FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<String>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) { //两个任务全都完成
                    System.out.println("Done");
                    executor.shutdown();                            //关闭线程池和服务
                    return;
                }

                if (!futureTask1.isDone()) { // 任务1没有完成，会等待，直到任务完成
                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }
                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    System.out.println("FutureTask2 output=" + s);
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }

        }

    }


    private static class MyCallable implements Callable<String> {

        private long waitTime;

        public MyCallable(int timeInMillis) {
            this.waitTime = timeInMillis;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(waitTime);
            return Thread.currentThread().getName();
        }
    }


}



