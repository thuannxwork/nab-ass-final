package com.nab.sso.controller;

import com.nab.common.constants.WebConstants;
import com.nab.common.response.ServerResponse;
import com.nab.sso.model.User;
import com.nab.sso.dto.UserDTO;
import com.nab.sso.service.UserDetailServiceImpl;
import com.nab.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl myUserDetailService;

    @PostMapping("/auth")
    public ResponseEntity<ServerResponse> createAuthToken(@RequestBody HashMap<String, String> credential) {
        return new ResponseEntity<>(userService.createAuthToken(credential), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ServerResponse> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/logout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @GetMapping(value = "/loadUserByUsername")
    public UserDetails loadUserByUsername(String s) {
        return myUserDetailService.loadUserByUsername(s);
    }

    @GetMapping("/getUserById")
    public UserDTO getUserById(@RequestParam(WebConstants.USER_ID) Long userId) {
        return new UserDTO(userService.findById(userId));
    }

}
