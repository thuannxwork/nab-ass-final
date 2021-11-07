package com.nab.sso.service;

import com.nab.common.constants.ResponseCode;
import com.nab.common.constants.WebConstants;
import com.nab.common.exception.UserCustomException;
import com.nab.common.response.ServerResponse;
import com.nab.sso.cache.CacheService;
import com.nab.sso.dto.Token;
import com.nab.sso.util.JwtUtil;
import com.nab.common.util.Validator;
import com.nab.sso.model.Authorities;
import com.nab.sso.model.User;
import com.nab.sso.repository.UserRepository;
import com.nab.sso.util.SSOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtutil;

    @Autowired
    CacheService cacheService;

    @Override
    public ServerResponse createAuthToken(HashMap<String, String> credential) {
        final String email = credential.get(WebConstants.USER_EMAIL);
        final String password = credential.get(WebConstants.USER_PASSWORD);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new UserCustomException("Invalid User Credentials");
        }
        final UserDetails userDetails = userDetailService.loadUserByUsername(email);
        final String jwt = jwtutil.generateToken(userDetails);

        jwtutil.checkToken(jwt, userDetails);

        ServerResponse resp = new ServerResponse();
        resp.setStatus(ResponseCode.SUCCESS_CODE);
        resp.setMessage(ResponseCode.SUCCESS_MESSAGE);
        resp.setAuthToken(jwt);

        if (userDetails != null && userDetails.getAuthorities() != null
                && userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            resp.setUserType("ADMIN");
        } else {
            resp.setUserType("CUSTOMER");
        }

        //Add token to redis cache
        cacheService.addToken2RedisCache(new Token(userDetails.getUsername(), jwt));

        return resp;
    }

    @Override
    public ServerResponse addUser(User user) {
        ServerResponse resp = new ServerResponse();

        Authorities authorities = new Authorities();
        authorities.setUsername(user.getUsername());
        authorities.setAuthority("ROLE_USER");
        user.setEnabled(true);
        user.setRoles(Arrays.asList(authorities));

        try {
            if (SSOValidator.isUserEmpty(user)) {
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
                resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
            } else if (!Validator.isValidEmail(user.getEmail())) {
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
                resp.setMessage(ResponseCode.INVALID_EMAIL_FAIL_MSG);
            } else {
                resp.setStatus(ResponseCode.SUCCESS_CODE);
                resp.setMessage(ResponseCode.CUST_REG);
                userRepo.save(user);
            }
        } catch (Exception e) {
            throw new UserCustomException("An error occured while saving user, please check details or try again");
        }
        return resp;
    }

    @Override
    public User findById(Long id) {
        Optional<User> opt = userRepo.findById(id);
        return opt == null ? null : opt.get();
    }
}
