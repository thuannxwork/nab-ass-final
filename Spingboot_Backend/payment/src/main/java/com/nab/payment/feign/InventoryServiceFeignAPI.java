package com.nab.payment.feign;

import com.nab.common.constants.WebConstants;
import com.nab.payment.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(WebConstants.FEIGN_CLIENT_INVENTORY)
public interface InventoryServiceFeignAPI {

    @GetMapping(value = "ping")
    String ping();

    @GetMapping("/user/getProductById/")
    ProductDTO getProductById(@RequestParam(WebConstants.PROD_ID) int productId);
}
