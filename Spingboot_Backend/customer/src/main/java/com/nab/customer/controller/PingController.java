package com.nab.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("ping")
@RefreshScope
public class PingController {
    @Autowired
    private Environment environment;

    @GetMapping
    public ResponseEntity<?> ping() throws Exception {
        return new ResponseEntity<String>(environment.getRequiredProperty("spring.application.name"), HttpStatus.OK);
    }
}
