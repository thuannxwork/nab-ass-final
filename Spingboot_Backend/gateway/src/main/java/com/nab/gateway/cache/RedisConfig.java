package com.nab.gateway.cache;

import com.nab.gateway.model.Token;
import com.nab.gateway.model.UserCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(password == null ? RedisPassword.none() : RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(database);
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(10));// 10s connection timeout

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return jedisConFactory;
    }

    @Bean
    RedisTemplate<String, UserCache> userRedis() {
        final RedisTemplate<String, UserCache> userRedis = new RedisTemplate<String, UserCache>();
        userRedis.setConnectionFactory(jedisConnectionFactory());
        userRedis.setValueSerializer(new Jackson2JsonRedisSerializer<UserCache>(UserCache.class));
        return userRedis;
    }

    @Bean
    RedisTemplate<String, Token> tokenRedis() {
        final RedisTemplate<String, Token> tokenRedis = new RedisTemplate<String, Token>();
        tokenRedis.setConnectionFactory(jedisConnectionFactory());
        tokenRedis.setValueSerializer(new Jackson2JsonRedisSerializer<Token>(Token.class));
        return tokenRedis;
    }
}
