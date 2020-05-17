package com.lingzhan.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 凌战 on 2020/1/16
 */
public class RedisTransactionTest {
    public static void main(String[] arg){
        String redisKey = "redisTest";
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        try {
            Jedis jedis = new Jedis("47.107.221.219",6379);
            jedis.set(redisKey,"0");
            jedis.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        for (int i=0;i<10000;i++){
            executorService.execute(()->{
                Jedis jedis1 = new Jedis("47.107.221.219",6379);
                try {
                    // 在multi之前,可以指定待监控的keys,在执行exec之前,如果被监控的keys发生修改,exec将放弃该事务队列中的所有命令
                    jedis1.watch(redisKey);
                    String redisValue = jedis1.get(redisKey);
                    int valInteger = Integer.valueOf(redisValue);
                    String userInfo = UUID.randomUUID().toString();
                    if (valInteger<20){
                        // 开启事务
                        Transaction transaction = jedis1.multi();
                        // multi之后,所有命令都将被存入命令队列,直到执行exec
                        transaction.incr(redisKey);
                        // 执行事务
                        List list = transaction.exec();
                        if (list!=null){
                            System.out.println("用户："+userInfo+"，秒杀成功！当前成功人数："+(valInteger+1));
                        }else {
                            System.out.println("用户："+userInfo+"，秒杀失败");
                        }
                    }else {
                        System.out.println("已经有20人秒杀成功，秒杀结束");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    jedis1.close();
                }
            });
        }
        executorService.shutdown();
    }

}
