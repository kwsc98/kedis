package pres.kedis.example.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pres.kedis.example.cache.CacheTemplate;
import pres.kedis.example.cache.redis.RedisCacheTemplate;
import pres.kedis.example.config.AppLicationConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author kwsc98
 */
@Component
@Slf4j
public class TokenService {

    @Resource
    private AppLicationConfig appLicationConfig;

    @Resource
    private RedisTemplate<String, String> redisTokenCacheTemplate;


    private CacheTemplate<String, String> cacheTemplate;

    @PostConstruct
    public void init() {
        cacheTemplate = new RedisCacheTemplate<String, String>().builder(redisTokenCacheTemplate);
    }

    public String getToken(String key) {
        return cacheTemplate.get(key);
    }

    public String setToken(String key, long timeOut) {
        UUID uuid = UUID.randomUUID();
        cacheTemplate.put(key, uuid.toString(), timeOut, TimeUnit.MINUTES);
        return uuid.toString();
    }


}
