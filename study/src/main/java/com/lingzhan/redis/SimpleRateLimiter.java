package com.lingzhan.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

/**
 * 简单限流
 * Created by 凌战 on 2020/2/18
 */
public class SimpleRateLimiter {

    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }


    /**
     *
     * @param userId 用户id
     * @param actionKey 用户行为类型
     * @param period 周期
     * @param maxCount 周期内允许行为出现次数
     * @return 是否允许添加
     */
    public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) throws IOException {
        // 同一个用户同一类Action放入一个zset中
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();

        Pipeline pipe = jedis.pipelined();
        // 开启事务
        pipe.multi();
        // 用户行为产生的时间戳作为score
        // zset中只有score值非常重要,value值没有特别的意义,保证唯一即可
        pipe.zadd(key, nowTs, "" + nowTs);
        // 移除在指定score间数据
        // 保留了最近 period 秒数据
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
        // 计算集合中保留数据数量
        Response<Long> count = pipe.zcard(key);
        // key的过期时间
        pipe.expire(key, period + 1);
        pipe.exec();
        pipe.close();
        return count.get() <= maxCount;

    }


    public static void main(String[] args) throws IOException {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        SimpleRateLimiter limiter = new SimpleRateLimiter(jedis);
        for(int i=0;i<20;i++) {
            System.out.println(limiter.isActionAllowed("laoqian", "reply", 60, 5));
        }
    }


}
