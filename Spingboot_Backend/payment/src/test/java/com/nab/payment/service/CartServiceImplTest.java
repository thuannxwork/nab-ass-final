package com.nab.payment.service;

import com.nab.common.constants.ResponseCode;
import com.nab.common.exception.CartCustomException;
import com.nab.common.response.ServerResponse;
import com.nab.payment.cache.CacheService;
import com.nab.payment.dto.ProductDTO;
import com.nab.payment.dto.UserCache;
import com.nab.payment.feign.InventoryServiceFeignAPI;
import com.nab.payment.model.CartItem;
import com.nab.payment.repository.CartItemRepository;
import com.nab.payment.response.CartResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.*;

class CartServiceImplTest {
    @Mock
    CartItemRepository cartRepo;
    @Mock
    CacheService cacheService;
    @Mock
    InventoryServiceFeignAPI inventoryServiceFeignAPI;
    @InjectMocks
    CartServiceImpl cartServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddToCartSuccess() {
        when(cacheService.getUserFromCache(anyString())).thenReturn(new UserCache());
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");
        when(inventoryServiceFeignAPI.getProductById(anyInt())).thenReturn(new ProductDTO());

        ServerResponse result = cartServiceImpl.addToCart("1");
        Assertions.assertEquals(ResponseCode.SUCCESS_CODE, result.getStatus());
    }

    @Test
    void testAddToCartException() {
        when(cacheService.getUserFromCache(anyString())).thenReturn(new UserCache());
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");
        when(inventoryServiceFeignAPI.getProductById(anyInt())).thenReturn(new ProductDTO());

        Assertions.assertThrows(CartCustomException.class,
                () -> {
                    cartServiceImpl.addToCart("test");
                });
    }

    @Test
    void testAddToCartUserNotFound() {
        when(cacheService.getUserFromCache(anyString())).thenReturn(new UserCache());
        when(cacheService.getUsernameFromHeader()).thenReturn(null);
        when(inventoryServiceFeignAPI.getProductById(anyInt())).thenReturn(new ProductDTO());

        Assertions.assertThrows(CartCustomException.class,
                () -> {
                    cartServiceImpl.addToCart("test");
                });
    }

    @Test
    void testAddToCartExceptionCacheNotFound() {
        when(cacheService.getUserFromCache(anyString())).thenReturn(null);
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");
        when(inventoryServiceFeignAPI.getProductById(anyInt())).thenReturn(new ProductDTO());

        Assertions.assertThrows(CartCustomException.class,
                () -> {
                    cartServiceImpl.addToCart("test");
                });
    }

    @Test
    void testViewCartSuccess() {
        UserCache userCache = new UserCache();
        userCache.setId(1L);
        when(cartRepo.findByUserId(anyLong())).thenReturn(Arrays.<CartItem>asList(new CartItem()));
        when(cacheService.getUserFromCache(anyString())).thenReturn(userCache);
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");
        when(inventoryServiceFeignAPI.getProductById(anyInt())).thenReturn(new ProductDTO());

        CartResponse result = cartServiceImpl.viewCart();
        Assertions.assertEquals(ResponseCode.SUCCESS_CODE, result.getStatus());
    }

    @Test
    void testViewCartException() {
        when(cartRepo.findByUserId(anyLong())).thenReturn(Arrays.<CartItem>asList(new CartItem()));
        when(cacheService.getUserFromCache(anyString())).thenReturn(null);
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");
        when(inventoryServiceFeignAPI.getProductById(anyInt())).thenReturn(new ProductDTO());

        Assertions.assertThrows(CartCustomException.class,
                () -> {
                    cartServiceImpl.viewCart();
                });
    }

    @Test
    void testUpdateCartSuccess() {
        UserCache userCache = new UserCache();
        userCache.setId(1L);
        when(cartRepo.findByUserId(anyLong())).thenReturn(Arrays.<CartItem>asList(new CartItem()));
        when(cartRepo.findByIdAndUserId(anyInt(), anyLong())).thenReturn(new CartItem());
        when(cacheService.getUserFromCache(anyString())).thenReturn(userCache);
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");

        CartResponse result = cartServiceImpl.updateCart(new HashMap<String, String>() {{
            put("id", "1");
            put("quantity", "1");
        }});
        Assertions.assertEquals(ResponseCode.SUCCESS_CODE, result.getStatus());
    }

    @Test
    void testUpdateCartException() {

        when(cacheService.getUsernameFromHeader()).thenReturn(null);

        Assertions.assertThrows(CartCustomException.class,
                () -> {
                    cartServiceImpl.updateCart(new HashMap<String, String>() {{
                        put("id", "1");
                        put("quantity", "1");
                    }});
                });
    }

    @Test
    void testDelCartSuccess() {
        when(cartRepo.findByUserId(anyLong())).thenReturn(Arrays.<CartItem>asList(new CartItem()));
        when(cacheService.getUserFromCache(anyString())).thenReturn(new UserCache());
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");

        CartResponse result = cartServiceImpl.delCart("1");
        Assertions.assertEquals(ResponseCode.SUCCESS_CODE, result.getStatus());
    }

    @Test
    void testDelCartException() {
        when(cartRepo.findByUserId(anyLong())).thenReturn(Arrays.<CartItem>asList(new CartItem()));
        when(cacheService.getUserFromCache(anyString())).thenReturn(new UserCache());
        when(cacheService.getUsernameFromHeader()).thenReturn("customer1");

        Assertions.assertThrows(CartCustomException.class,
                () -> {
                    cartServiceImpl.delCart("cartId");
                });
    }
}

