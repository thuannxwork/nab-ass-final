package com.nab.payment.controller;

import com.nab.common.constants.WebConstants;
import com.nab.common.response.ServerResponse;
import com.nab.payment.response.CartResponse;
import com.nab.payment.service.CartService;
import com.nab.payment.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.logging.Logger;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrdersService ordersService;


    @GetMapping("/addToCart")
    public ResponseEntity<ServerResponse> addToCart(@RequestParam(WebConstants.PROD_ID) String productId) {
        return new ResponseEntity<>(cartService.addToCart(productId), HttpStatus.OK);
    }

    @GetMapping("/viewCart")
    public ResponseEntity<CartResponse> viewCart() {
        return new ResponseEntity<>(cartService.viewCart(), HttpStatus.OK);
    }

    @PutMapping("/updateCart")
    public ResponseEntity<CartResponse> updateCart(@RequestBody HashMap<String, String> cart) {
        return new ResponseEntity<>(cartService.updateCart(cart), HttpStatus.OK);
    }

    @DeleteMapping("/delCart")
    public ResponseEntity<CartResponse> delCart(@RequestParam(name = WebConstants.BUF_ID) String cartId) {
        return new ResponseEntity<>(cartService.delCart(cartId), HttpStatus.OK);
    }

    @GetMapping("/placeOrder")
    public ResponseEntity<ServerResponse> placeOrder() {
        return new ResponseEntity<>(ordersService.placeOrder(), HttpStatus.OK);
    }
}
