package com.nab.payment.feign;

import com.nab.common.constants.WebConstants;
import com.nab.payment.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(WebConstants.FEIGN_CLIENT_SSO)
public interface SSOServiceFeignAPI {

    @GetMapping(value = "ping")
    String ping();

    @GetMapping("/home/getUserById")
    UserDTO getUserById(@RequestParam(WebConstants.USER_ID) Long userId);
}
