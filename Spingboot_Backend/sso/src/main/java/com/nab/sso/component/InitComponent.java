package com.nab.sso.component;

import com.nab.sso.cache.CacheService;
import com.nab.sso.model.User;
import com.nab.sso.dto.UserCache;
import com.nab.sso.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitComponent {
    private static final Logger LOGGER = LogManager.getLogger(InitComponent.class);

    @Autowired
    CacheService cacheService;

    @Autowired
    UserRepository userRepository;


    @PostConstruct
    public void initCache() {
        try {
            List<User> userList = userRepository.findAll();
            if (userList != null) {
                userList.forEach(x -> cacheService.addUser2RedisCache(new UserCache(x)));
                LOGGER.info("pushAllUserToRedisCache >>> size = " + userList.size());
            } else
                LOGGER.info("pushAllUserToRedisCache >>> size = 0");
        } catch (Exception e) {
            LOGGER.error("Exception when pushAllUserToRedisCache", e);
        }
    }
}
