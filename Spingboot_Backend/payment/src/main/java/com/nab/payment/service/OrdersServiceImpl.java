package com.nab.payment.service;

import com.nab.common.constants.ResponseCode;
import com.nab.common.exception.OrderCustomException;
import com.nab.common.exception.PlaceOrderCustomException;
import com.nab.common.exception.UserCustomException;
import com.nab.common.response.ServerResponse;
import com.nab.common.util.Validator;
import com.nab.payment.cache.CacheService;
import com.nab.payment.dto.OrderDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrderRepository ordRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private InventoryServiceFeignAPI inventoryServiceFeignAPI;

    @Autowired
    private SSOServiceFeignAPI ssoServiceFeignAPI;

    @Override
    public ViewOrderResponse viewOrders() {
        ViewOrderResponse resp = new ViewOrderResponse();
        try {
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.VIEW_SUCCESS_MESSAGE);
            List<OrderDTO> orderList = new ArrayList<>();
            List<Orders> poList = ordRepo.findAll();
            poList.forEach((po) -> {
                OrderDTO ord = new OrderDTO();
                UserDTO user = ssoServiceFeignAPI.getUserById(po.getUserId());
                if (user != null) {
                    ord.setOrderBy(user.getUsername());
                }
                ord.setOrderId(po.getOrderId());
                ord.setOrderStatus(po.getOrderStatus());
                List<OrderItem> lst = orderItemRepository.findAllByOrderId(po.getOrderId());
                lst.forEach(x -> {
                    x.setProduct(inventoryServiceFeignAPI.getProductById(x.getProductId().intValue()));
                });
                ord.setProducts(lst);
                orderList.add(ord);
            });
            resp.setOrderlist(orderList);
        } catch (Exception e) {
            throw new OrderCustomException("Unable to retrieve orders, please try again");
        }
        return resp;
    }

    @Override
    public ServerResponse updateOrders(String orderId, String orderStatus) {
        ServerResponse resp = new ServerResponse();

        if (Validator.isStringEmpty(orderId) || Validator.isStringEmpty(orderStatus)) {
            resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
            resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
            throw new OrderCustomException("Lack required field");
        }
        try {
            Orders pc = ordRepo.findByOrderId(Integer.parseInt(orderId));
            pc.setOrderStatus(orderStatus);
            pc.setOrderDate(new Date(System.currentTimeMillis()));
            ordRepo.save(pc);
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.UPD_ORD_SUCCESS_MESSAGE);
        } catch (Exception e) {
            throw new OrderCustomException("Unable to retrieve orders, please try again");
        }

        return resp;
    }

    @Override
    public ServerResponse placeOrder() {
        ServerResponse resp = new ServerResponse();
        try {

            UserCache userCache = getUserCacheFromHeader();

            Orders order = new Orders();
            order.setUserId(userCache.getId());
            order.setOrderDate(new Date());
            order.setOrderStatus(ResponseCode.ORD_STATUS_CODE);
            order.setPaymentModeId(ResponseCode.ORD_PAYMENT_MODE_CASH);
            order.setPaymentStatus(ResponseCode.ORD_PAYMENT_STATUS_NOT_PAID);

            double total = 0;
            List<CartItem> cartItemList = cartItemRepo.findAllByUserId(userCache.getId());
            for (CartItem buf : cartItemList) {
                total = +(buf.getQuantity() * buf.getPrice());
            }
            order.setTotal(total);
            Orders res = ordRepo.save(order);
            cartItemList.forEach(cartItem -> {

                // Save to order
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(res.getOrderId());
                orderItem.setProductId((long) cartItem.getProductId());
                orderItem.setPrice(cartItem.getPrice());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setTotal(cartItem.getPrice() * cartItem.getQuantity());
                orderItemRepository.save(orderItem);

                // Remove from cart
                cartItemRepo.delete(cartItem);
            });

            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.ORD_SUCCESS_MESSAGE);
        } catch (Exception e) {
            throw new PlaceOrderCustomException("Unable to place order, please try again later");
        }
        return resp;
    }

    private UserCache getUserCacheFromHeader() {
        String username = cacheService.getUsernameFromHeader();
        if (StringUtils.isEmpty(username)) {
            throw new UserCustomException("user not found");
        }
        UserCache userCache = cacheService.getUserFromCache(username);
        if (userCache == null) {
            throw new UserCustomException("user invalid");
        }
        return userCache;
    }
}
