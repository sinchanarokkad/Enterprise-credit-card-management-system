package com.sinchana.credit_card_management_service.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void set(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null && type.isAssignableFrom(value.getClass())) {
            return type.cast(value);
        }
        return null;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setExpire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public void increment(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public void increment(String key, long delta) {
        redisTemplate.opsForValue().increment(key, delta);
    }
}
