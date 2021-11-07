package com.nab.payment.service;

import com.nab.common.constants.ResponseCode;
import com.nab.common.exception.CartCustomException;
import com.nab.common.exception.UserCustomException;
import com.nab.common.response.ServerResponse;
import com.nab.payment.cache.CacheService;
import com.nab.payment.feign.InventoryServiceFeignAPI;
import com.nab.payment.model.CartItem;
import com.nab.payment.dto.ProductDTO;
import com.nab.payment.dto.UserCache;
import com.nab.payment.repository.CartItemRepository;
import com.nab.payment.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartRepo;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private InventoryServiceFeignAPI inventoryServiceFeignAPI;

    @Override
    public ServerResponse addToCart(String productId) {
        ServerResponse resp = new ServerResponse();
        try {
            String username = cacheService.getUsernameFromHeader();
            if (StringUtils.isEmpty(username)) {
                throw new UserCustomException("user not found");
            }
            UserCache userCache = cacheService.getUserFromCache(username);
            if (userCache == null) {
                throw new UserCustomException("user invalid");
            }

            // Call to inventory service to get product information
            ProductDTO product = inventoryServiceFeignAPI.getProductById(Integer.parseInt(productId));

            CartItem buf = new CartItem();
            buf.setUserId(userCache.getId());
            buf.setQuantity(1L);
            buf.setPrice(product.getPrice());
            buf.setProductId(Integer.parseInt(productId));
            Date date = new Date();
            buf.setDateAdded(date);

            cartRepo.save(buf);

            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.CART_UPD_MESSAGE_CODE);
        } catch (Exception e) {
            throw new CartCustomException("Unable to add product to cart, please try again");
        }
        return resp;
    }

    @Override
    public CartResponse viewCart() {
        CartResponse resp = new CartResponse();
        try {
            UserCache userCache = getUserCacheFromHeader();

            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.VW_CART_MESSAGE);
            List<CartItem> lst = cartRepo.findByUserId(userCache.getId());
            lst.forEach(x -> {
                x.setProduct(inventoryServiceFeignAPI.getProductById(x.getProductId()));
            });
            resp.setOblist(lst);
        } catch (Exception e) {
            throw new CartCustomException("Unable to retrieve cart items, please try again");
        }
        return resp;
    }

    @Override
    public CartResponse updateCart(HashMap<String, String> cart) {
        CartResponse resp = new CartResponse();
        try {
            UserCache userCache = getUserCacheFromHeader();

            CartItem selCartItem = cartRepo.findByIdAndUserId(Integer.parseInt(cart.get("id")), userCache.getId());
            selCartItem.setQuantity(Long.parseLong(cart.get("quantity")));
            cartRepo.save(selCartItem);

            List<CartItem> cartItemList = cartRepo.findByUserId(userCache.getId());
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.UPD_CART_MESSAGE);
            resp.setOblist(cartItemList);
        } catch (Exception e) {
            throw new CartCustomException("Unable to update cart items, please try again");
        }
        return resp;
    }

    @Override
    public CartResponse delCart(String cartId) {
        CartResponse resp = new CartResponse();
        try {
            UserCache userCache = getUserCacheFromHeader();
            cartRepo.deleteByIdAndUserId(Integer.parseInt(cartId), userCache.getId());

            List<CartItem> cartItemList = cartRepo.findByUserId(userCache.getId());
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.DEL_CART_SUCCESS_MESSAGE);
            resp.setOblist(cartItemList);
        } catch (Exception e) {
            throw new CartCustomException("Unable to delete cart items, please try again");
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
