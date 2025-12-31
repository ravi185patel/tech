package com.redis.rediscrud.service;

import com.redis.rediscrud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UserCacheService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public static final String KEY_PREFIX = "USER:";

    /* SAVE USER */
    public void saveUser(User user){
        String key = KEY_PREFIX + user.getUserId();
        redisTemplate.opsForValue().set(key,user, Duration.ofMinutes(10));
    }

    /* FETCH USER */
    public User getUser(String userId){
        String key = KEY_PREFIX + userId;

        return (User)redisTemplate.opsForValue().get(key);
    }

    /* DELETE USER */
    public void deleteUser(String userId){
        String key = KEY_PREFIX + userId;
        redisTemplate.delete(key);
    }
}
