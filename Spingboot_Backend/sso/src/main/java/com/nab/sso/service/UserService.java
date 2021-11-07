package com.nab.sso.service;


import com.nab.common.response.ServerResponse;
import com.nab.sso.model.User;

import java.util.HashMap;

public interface UserService {
    ServerResponse createAuthToken(HashMap<String, String> credential);

    ServerResponse addUser(User user);

    User findById(Long id);
}




