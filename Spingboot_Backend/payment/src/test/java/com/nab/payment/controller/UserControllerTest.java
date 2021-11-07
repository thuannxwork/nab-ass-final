package com.nab.payment.controller;

import com.nab.common.response.ServerResponse;
import com.nab.payment.response.CartResponse;
import com.nab.payment.service.CartService;
import com.nab.payment.service.OrdersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    CartService cartService;
    @Mock
    OrdersService ordersService;
    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddToCart() {
        when(cartService.addToCart(anyString())).thenReturn(new ServerResponse());

        ResponseEntity<ServerResponse> result = userController.addToCart("productId");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testViewCart() {
        when(cartService.viewCart()).thenReturn(new CartResponse());

        ResponseEntity<CartResponse> result = userController.viewCart();
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdateCart() {
        when(cartService.updateCart(any())).thenReturn(new CartResponse());

        ResponseEntity<CartResponse> result = userController.updateCart(new HashMap<String, String>() {{
            put("String", "String");
        }});
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testDelCart() {
        when(cartService.delCart(anyString())).thenReturn(new CartResponse());

        ResponseEntity<CartResponse> result = userController.delCart("cartId");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testPlaceOrder() {
        when(ordersService.placeOrder()).thenReturn(new ServerResponse());

        ResponseEntity<ServerResponse> result = userController.placeOrder();
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}

