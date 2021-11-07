package com.nab.payment.service;

import com.nab.common.constants.ResponseCode;
import com.nab.common.exception.OrderCustomException;
import com.nab.common.exception.PlaceOrderCustomException;
import com.nab.common.response.ServerResponse;
import com.nab.payment.cache.CacheService;
import com.nab.payment.dto.ProductDTO;
import com.nab.payment.dto.UserCache;
import com.nab.payment.dto.UserDTO;
import com.nab.payment.feign.InventoryServiceFeignAPI;
import com.nab.payment.feign.SSOServiceFeignAPI;
import com.nab.payment.model.CartItem;
import com.nab.payment.model.OrderItem;
import com.nab.payment.model.Orders;
import com.nab.payment.repository.CartItemRepository;
import com.nab.payment.repository.OrderItemRepository;
import com.nab.payment.repository.OrderRepository;
import com.nab.payment.response.ViewOrderResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

class OrdersServiceImplTest {
    @Mock
    OrderRepository ordRepo;
    @Mock
    CartItemRepository cartItemRepo;
    @Mock
    OrderItemRepository orderItemRepository;
    @Mock
    CacheService cacheService;
    @Mock
    InventoryServiceFeignAPI inventoryServiceFeignAPI;
    @Mock
    SSOServiceFeignAPI ssoServiceFeignAPI;
    @InjectMocks
    OrdersServiceImpl ordersServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testViewOrdersSuccess() {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(1L);

        when(ordRepo.findAll()).thenReturn(Arrays.<Orders>asList(new Orders(0, "orderStatus", null, 0d, 1L)));
        when(orderItemRepository.findAllByOrderId(anyInt())).thenReturn(Arrays.<OrderItem>asList(orderItem));
        when(inventoryServiceFeignAPI.getProductById(anyInt())).thenReturn(new ProductDTO());
        when(ssoServiceFeignAPI.getUserById(anyLong())).thenReturn(new UserDTO(Long.valueOf(1), "email", "username", "password", "type"));

        ViewOrderResponse result = ordersServiceImpl.viewOrders();
        Assertions.assertEquals(ResponseCode.SUCCESS_CODE, result.getStatus());
    }

    @Test
    void testViewOrdersException() {
        when(ordRepo.findAll()).thenReturn(Arrays.<Orders>asList(new Orders(0, "orderStatus", null, 0d)));
        when(orderItemRepository.findAllByOrderId(anyInt())).thenReturn(Arrays.<OrderItem>asList(new OrderItem()));
        when(inventoryServiceFeignAPI.getProductById(anyInt())).thenReturn(new ProductDTO());
        when(ssoServiceFeignAPI.getUserById(anyLong())).thenReturn(new UserDTO(Long.valueOf(1), "email", "username", "password", "type"));
        Assertions.assertThrows(OrderCustomException.class,
                () -> {
                    ordersServiceImpl.viewOrders();
                });
    }

    @Test
    void testUpdateOrdersSuccess() {
        when(ordRepo.findByOrderId(anyInt())).thenReturn(new Orders(0, "orderStatus", new Date(), 0d));

        ServerResponse result = ordersServiceImpl.updateOrders("1", "PENDING");
        Assertions.assertEquals(ResponseCode.SUCCESS_CODE, result.getStatus());
    }

    @Test
    void testUpdateOrdersException() {
        Assertions.assertThrows(OrderCustomException.class,
                () -> {
                    ordersServiceImpl.updateOrders("orderId", "orderStatus");
                });
    }

    @Test
    void testUpdateOrdersExceptionLackRequireField() {
        Assertions.assertThrows(OrderCustomException.class,
                () -> {
                    ordersServiceImpl.updateOrders("", "PENDING");
                });
    }

    @Test
    void testPlaceOrderSuccess() {
        UserCache userCache = new UserCache();
        userCache.setId(1L);
        CartItem cartItem = new CartItem();
        cartItem.setPrice(1);
        cartItem.setQuantity(1L);
        cartItem.setProductId(1);

        when(cartItemRepo.findAllByUserId(anyLong())).thenReturn(Arrays.asList(cartItem));
        when(cacheService.getUserFromCache(anyString())).thenReturn(userCache);
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");
        when(ordRepo.save(any())).thenReturn(new Orders());
        doNothing().when(cartItemRepo).delete(any());

        ServerResponse result = ordersServiceImpl.placeOrder();
        Assertions.assertEquals(ResponseCode.SUCCESS_CODE, result.getStatus());
    }

    @Test
    void testPlaceOrderException() {
        when(cartItemRepo.findAllByUserId(anyLong())).thenReturn(Arrays.asList(new CartItem()));
        when(cacheService.getUserFromCache(anyString())).thenReturn(null);
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");
        when(ordRepo.save(any())).thenReturn(new Orders());
        doNothing().when(cartItemRepo).delete(any());

        Assertions.assertThrows(PlaceOrderCustomException.class,
                () -> {
                    ordersServiceImpl.placeOrder();
                });
    }

    @Test
    void testPlaceOrderExceptionUserNotFound() {
        UserCache userCache = new UserCache();
        userCache.setId(1L);

        when(cartItemRepo.findAllByUserId(anyLong())).thenReturn(Arrays.asList(new CartItem()));
        when(cacheService.getUserFromCache(anyString())).thenReturn(userCache);
        when(cacheService.getUsernameFromHeader()).thenReturn(null);
        when(ordRepo.save(any())).thenReturn(new Orders());
        doNothing().when(cartItemRepo).delete(any());

        Assertions.assertThrows(PlaceOrderCustomException.class,
                () -> {
                    ordersServiceImpl.placeOrder();
                });
    }
}

