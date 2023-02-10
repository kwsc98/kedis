package pres.kedis.example.configuration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import pres.kedis.example.config.AppLicationConfig;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author kwsc98
 */
@Configuration
public class RedisConfiguration {

    @Resource
    private AppLicationConfig appLicationConfig;

    @Bean("redisTokenCacheTemplate")
    public RedisTemplate<String, String> redisCacheTemplate(@Qualifier("redisTokenLettuceConnectionFactory")LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean("redisTokenLettuceConnectionFactory")
    public LettuceConnectionFactory initLettuceConnectionFactory() {
        //redis配置
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(appLicationConfig.getRedisHost(), appLicationConfig.getRedisPort());
        redisConfiguration.setDatabase(appLicationConfig.getRedisDatabase());
        redisConfiguration.setPassword(RedisPassword.of(appLicationConfig.getRedisPassword()));
        //连接池配置
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxIdle(16);
        genericObjectPoolConfig.setMinIdle(4);
        genericObjectPoolConfig.setMaxTotal(32);
        //redis客户端配置
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(10000));
        builder.shutdownTimeout(Duration.ofMillis(4000));
        builder.poolConfig(genericObjectPoolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();
        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

}
