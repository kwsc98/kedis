package pres.kedis.example.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;
import pres.kedis.example.cache.CacheTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author kwsc98
 */
public class RedisCacheTemplate<K, V> implements CacheTemplate<K, V> {

    RedisTemplate<K, V> redisTemplate;


    public RedisCacheTemplate<K, V> builder(RedisTemplate<K, V> o) {
        redisTemplate = o;
        return this;
    }

    @Override
    public void put(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(K key, V value, long timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeOut, timeUnit);
    }

    @Override
    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }
}
