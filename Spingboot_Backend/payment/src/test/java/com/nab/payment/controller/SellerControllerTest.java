package com.nab.payment.controller;

import com.nab.common.response.ServerResponse;
import com.nab.payment.response.ViewOrderResponse;
import com.nab.payment.service.OrdersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

class SellerControllerTest {
    @Mock
    OrdersService ordersService;
    @InjectMocks
    SellerController sellerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testViewOrders() {
        when(ordersService.viewOrders()).thenReturn(new ViewOrderResponse());

        ResponseEntity<ViewOrderResponse> result = sellerController.viewOrders();
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdateOrders() {
        when(ordersService.updateOrders(anyString(), anyString())).thenReturn(new ServerResponse());

        ResponseEntity<ServerResponse> result = sellerController.updateOrders("orderId", "orderStatus");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}

