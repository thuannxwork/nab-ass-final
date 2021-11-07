package com.nab.payment.cache;

import com.nab.payment.dto.Token;
import com.nab.payment.dto.UserCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class CacheServiceTest {
    @Mock
    RedisTemplate<String, UserCache> userRedis;
    @Mock
    RedisTemplate<String, Token> tokenRedis;
    @Mock
    HttpServletRequest request;
    @InjectMocks
    CacheService cacheService;
    @Mock
    private ValueOperations valueOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddUser2RedisCache() {
        when(userRedis.opsForValue()).thenReturn(valueOperations);
        cacheService.addUser2RedisCache(new UserCache());
    }

    @Test
    void testGetUserFromCache() {
        UserCache result = cacheService.getUserFromCache("username");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetUserFromCacheHasKey() {
        when(userRedis.opsForValue()).thenReturn(valueOperations);
        when(userRedis.hasKey(anyString())).thenReturn(true);
        UserCache result = cacheService.getUserFromCache("username");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testAddToken2RedisCache() {
        when(tokenRedis.opsForValue()).thenReturn(valueOperations);
        cacheService.addToken2RedisCache(new Token("username", "accessToken"));
    }

    @Test
    void testRemoveToken2RedisCache() {
        when(tokenRedis.opsForValue()).thenReturn(valueOperations);
        cacheService.removeToken2RedisCache(new Token("username", "accessToken"));
    }

    @Test
    void testGetTokenFromCache() {
        Token result = cacheService.getTokenFromCache("accessToken");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetTokenFromCacheHasKey() {
        Token expected = new Token("test", "test");
        when(tokenRedis.opsForValue()).thenReturn(valueOperations);
        when(tokenRedis.hasKey(anyString())).thenReturn(true);
        when(valueOperations.get(anyString())).thenReturn(expected);
        Token result = cacheService.getTokenFromCache("accessToken");
        Assertions.assertEquals(expected, result);
    }


    @Test
    void testGetUserFromAccessToken() {
        UserCache result = cacheService.getUserFromAccessToken("accessToken");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetUserFromAccessTokenHasKey() {
        Token expected = new Token("test", "test");
        when(tokenRedis.opsForValue()).thenReturn(valueOperations);
        when(tokenRedis.hasKey(anyString())).thenReturn(true);
        when(valueOperations.get(anyString())).thenReturn(expected);
        UserCache result = cacheService.getUserFromAccessToken("accessToken");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetHeaderValue() {
        String result = cacheService.getHeaderValue("headerName");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetUsernameFromHeader() {
        when(request.getHeader(anyString())).thenReturn("result");
        String result = cacheService.getUsernameFromHeader();
        Assertions.assertEquals("result", result);
    }

    @Test
    void testGetUsernameFromHeaderException() {
        when(request.getHeader(anyString())).thenThrow();
        String result = cacheService.getUsernameFromHeader();
        Assertions.assertEquals(null, result);
    }
}

