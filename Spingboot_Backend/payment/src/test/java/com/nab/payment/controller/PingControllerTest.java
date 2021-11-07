package com.nab.payment.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

class PingControllerTest {
    @Mock
    Environment environment;
    @InjectMocks
    PingController pingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPing() throws Exception {
        ResponseEntity<?> result = pingController.ping();
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}

