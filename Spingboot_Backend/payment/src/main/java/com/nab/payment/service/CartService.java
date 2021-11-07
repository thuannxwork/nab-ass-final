package com.nab.payment.service;

import com.nab.common.response.ServerResponse;
import com.nab.payment.response.CartResponse;

import java.util.HashMap;

public interface CartService {

    ServerResponse addToCart(String productId);

    CartResponse viewCart();

    CartResponse updateCart(HashMap<String, String> cart);

    CartResponse delCart(String cartId);
}




