package com.slsale.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Auther:
 * @Date:2021/4/17
 * @Description:com.slsale.commons
 * @Version:1.0
 */
@Repository(value = "redisAPI")
public class RedisAPI {

    @Autowired
    private JedisPool jedisPool;

    //将jedis返还到jedis连接池 JedisPool 中
    public static void returnResource(JedisPool jedisPool, Jedis jedis){
        if (jedis != null){
            jedisPool.returnResource(jedis);
        }
    }

    //判断key是否存在
    public boolean exists(String key){
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //根据key 设置value值
    public boolean set(String key,String value){
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //根据key取出value值
    public String get(String key){
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //回收jedis
            returnResource(this.jedisPool,jedis);
        }
        return value;
    }
    public boolean del(String key){
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
