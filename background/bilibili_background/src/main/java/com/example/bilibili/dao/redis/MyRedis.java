package com.example.bilibili.dao.redis;

import com.example.bilibili.util.redis.MyRedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyRedis {
    private RedisTemplate redisTemplate;
    public Boolean deleteByKey(RedisTemplate redisTemplate,String key){
        if(this.isRedisConnected()){
            return redisTemplate.delete(key);
        }
        return false;
    }

    @Autowired
    private RedisConnectionFactory factory;

    public boolean isRedisConnected() {
        try {
            RedisConnection connection = factory.getConnection();
            connection.ping();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Object takeValue(String key,RedisTemplate redisTemplate) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean checkExist(String key,RedisTemplate redisTemplate){
        return redisTemplate.hasKey(MyRedisKeys.VIDEO_GROUP_INDEX);
    }
    public void putValue(String key, String json, RedisTemplate redisTemplate) {
        if(this.checkExist(key,redisTemplate)){
            this.deleteByKey(redisTemplate,key);
            redisTemplate.opsForValue().set(key,json);
        }
    }
}
