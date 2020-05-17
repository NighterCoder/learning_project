package com.lingzhan.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

/**
 * 延时消息队列
 * 使用zset实现,score就用消息的到期处理时间
 *
 * 没有ack机制,如果对可靠性有极致的追求,不适合使用
 *
 *
 *
 * Created by 凌战 on 2019/12/16
 */
public class RedisDelayingQueue<T> {

    static class TaskItem<T> {
        public String id;
        public T msg;
    }


    private Jedis jedis;
    private String queueKey;


    public RedisDelayingQueue(Jedis jedis, String queueKey) {
        this.jedis = jedis;
        this.queueKey = queueKey;
    }

    //1.放入队列
    public void delay(T msg) {
        TaskItem<T> task = new TaskItem<>();
        task.id = UUID.randomUUID().toString(); //分配唯一的uuid
        task.msg = msg;
        String s = JSON.toJSONString(task); //fastjson 序列化
        jedis.zadd(queueKey, System.currentTimeMillis() + 5000, s); //塞入延时队列,5s后再取
    }


    public void loop() {
        while (!Thread.interrupted()) {
            //只取一条
            //zrangeByScore 查找给定范围[0,System.currentTimeMillis()] 从小到大 offset表示偏移量(从第几个开始) count表示取几条数据
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(5000);       //这里在队列为空时,睡眠5秒
                } catch (InterruptedException e) {
                    break;
                }
                //5s后重试
                System.out.println("5秒后重试");
                continue;
            }

            String s = values.iterator().next();
            //删除元素s
            //同一个任务可能会被多个进程线程抢到,通过zrem来决定唯一的属主
            if (jedis.zrem(queueKey, s) > 0) {
                //泛型需要使用TypeReference
                TaskItem<T> task = JSON.parseObject(s, new TypeReference<TaskItem<T>>(){});
                this.handleMsg(task.msg);
            }
        }
    }


    private void handleMsg(T msg) {
        System.out.println("输出msg:"+msg);
    }


    public static void main(String[] args) {
        Jedis jedis=new Jedis("127.0.0.1",6379);

        RedisDelayingQueue<String> queue=new RedisDelayingQueue<>(jedis,"q-demo");

        //生产者线程
        Thread producer=new Thread(){
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    queue.delay("进入队列"+i);
                }
            }
        };

        //消费者线程
        Thread consumer=new Thread(){
            @Override
            public void run() {
                queue.loop();
            }
        };

        producer.start();
        consumer.start();

        try{
            producer.join();
            Thread.sleep(6000);
            consumer.interrupt();
            Thread.sleep(6000);
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
