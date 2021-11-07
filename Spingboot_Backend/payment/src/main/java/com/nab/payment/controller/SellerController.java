package com.nab.payment.controller;

import com.nab.common.constants.ResponseCode;
import com.nab.common.constants.WebConstants;
import com.nab.common.response.ServerResponse;
import com.nab.payment.response.ViewOrderResponse;
import com.nab.payment.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/seller")
public class SellerController {


    @Autowired
    private OrdersService ordersService;


    @GetMapping("/viewOrders")
    public ResponseEntity<ViewOrderResponse> viewOrders() {
        return new ResponseEntity<>(ordersService.viewOrders(), HttpStatus.OK);
    }

    @PostMapping("/updateOrder")
    public ResponseEntity<ServerResponse> updateOrders(@RequestParam(name = WebConstants.ORD_ID) String orderId,
                                                       @RequestParam(name = WebConstants.ORD_STATUS) String orderStatus) {
        return new ResponseEntity<>(ordersService.updateOrders(orderId, orderStatus), HttpStatus.OK);
    }
}
