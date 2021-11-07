package com.nab.sso.service;

import com.nab.sso.dto.MyUserDetails;
import com.nab.sso.model.User;
import com.nab.sso.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<User> user = userRepository.findByUsername(s);

        user.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.map(MyUserDetails::new).get();
    }

}
