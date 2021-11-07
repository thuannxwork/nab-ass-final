package com.nab.payment.service;

import com.nab.common.response.ServerResponse;
import com.nab.payment.response.ViewOrderResponse;

public interface OrdersService {
    ViewOrderResponse viewOrders();

    ServerResponse updateOrders(String orderId, String orderStatus);

    ServerResponse placeOrder();
}




