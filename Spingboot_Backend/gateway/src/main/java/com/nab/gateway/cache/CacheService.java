package com.nab.gateway.cache;

import com.nab.gateway.model.Token;
import com.nab.gateway.model.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {
    @Autowired
    RedisTemplate<String, UserCache> userRedis;

    @Autowired
    RedisTemplate<String, Token> tokenRedis;

    @Autowired
    HttpServletRequest request;

    public void addUser2RedisCache(UserCache userCache) {
        final ValueOperations<String, UserCache> opsForValue = userRedis.opsForValue();
        String key = "USER" + userCache.getUsername();
        opsForValue.set(key, userCache, 365, TimeUnit.DAYS);
    }

    public UserCache getUserFromCache(String username) {
        final ValueOperations<String, UserCache> opsForValue = userRedis.opsForValue();
        if (userRedis.hasKey("USER" + username))
            return opsForValue.get("USER" + username);
        else return null;
    }

    public void addToken2RedisCache(Token token) {
        final ValueOperations<String, Token> opsForValue = tokenRedis.opsForValue();
        opsForValue.set(token.getAccessToken(), token, 1000 * 60 * 60 * 10, TimeUnit.MILLISECONDS);
    }

    public void removeToken2RedisCache(Token token) {
        final ValueOperations<String, Token> opsForValue = tokenRedis.opsForValue();
        opsForValue.set(token.getAccessToken(), token, 1, TimeUnit.MILLISECONDS);
    }

    public Token getTokenFromCache(String accessToken) {
        final ValueOperations<String, Token> opsForValue = tokenRedis.opsForValue();
        if (tokenRedis.hasKey(accessToken))
            return opsForValue.get(accessToken);
        else return null;
    }


    public UserCache getUserFromAccessToken(String accessToken) {
        final ValueOperations<String, Token> opsForValue = tokenRedis.opsForValue();
        if (tokenRedis.hasKey(accessToken)) {
            Token token = opsForValue.get(accessToken);
            if (token != null && token.getUsername() != null)
                return getUserFromCache(token.getUsername());
        }
        return null;
    }

    public String getHeaderValue(String headerName) {
        try {
            return request.getHeader(headerName);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsernameFromHeader() {
        return getHeaderValue("USERNAME");
    }

}
