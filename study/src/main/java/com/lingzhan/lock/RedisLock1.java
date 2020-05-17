package com.lingzhan.lock;

import java.util.Collections;
import java.util.UUID;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * Created by 凌战 on 2020/4/28
 */
public class RedisLock1 {

  private static String LOCK_KEY="redis_lock";
  protected static long INTERNAL_LOCK_LEASE_TIME=3;
  private long timeout=1000;
  // 就是setex命令
  private static SetParams params=SetParams.setParams().nx().px(INTERNAL_LOCK_LEASE_TIME);
  private static Integer inventory=1001;


  public static void main(String[] args) {


    //连接池配置对象,包含了很多默认配置
    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    JedisPool jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);

    for (int i = 0; i <=1000  ; i++) {

      new Thread(new Runnable() {
        @Override
        public void run() {
          try (Jedis jedis = jedisPool.getResource()) {
            String id = UUID.randomUUID().toString();
            lock(jedis, id);
            inventory--;
            System.out.println("当前库存:" + inventory);
            unlock(jedis, RedisLock1.LOCK_KEY, id);
          }
        }
      }).start();
    }
  }




  public static boolean lock(Jedis jedis,String id){
    String res=jedis.set(RedisLock1.LOCK_KEY,id,params);
    return "OK".equalsIgnoreCase(res);

  }


  public static boolean unlock(Jedis jedis, String lockKey, String requestId) {

    String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

    if ("1".equals(result)) {
      return true;
    }
    return false;

  }

}
