package com.nab.payment.cache;

import com.nab.payment.dto.Token;
import com.nab.payment.dto.UserCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;

class RedisConfigTest {
    RedisConfig redisConfig = new RedisConfig();

    @Test
    void testJedisConnectionFactory() {
        ReflectionTestUtils.setField(redisConfig, "host", "localhost");
        ReflectionTestUtils.setField(redisConfig, "port", 6379);
        JedisConnectionFactory result = redisConfig.jedisConnectionFactory();
        Assertions.assertNotNull(result);
    }

    @Test
    void testUserRedis() {
        ReflectionTestUtils.setField(redisConfig, "host", "localhost");
        ReflectionTestUtils.setField(redisConfig, "port", 6379);
        RedisTemplate<String, UserCache> result = redisConfig.userRedis();
        Assertions.assertNotNull(result);
    }

    @Test
    void testTokenRedis() {
        ReflectionTestUtils.setField(redisConfig, "host", "localhost");
        ReflectionTestUtils.setField(redisConfig, "port", 6379);
        RedisTemplate<String, Token> result = redisConfig.tokenRedis();
        Assertions.assertNotNull(result);
    }
}

