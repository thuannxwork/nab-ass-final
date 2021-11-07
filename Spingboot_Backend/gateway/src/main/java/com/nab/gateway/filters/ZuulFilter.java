package com.nab.gateway.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulFilter {
    @Bean
    public PreFilter authenticationPreFilter() {
        return new PreFilter();
    }
}
