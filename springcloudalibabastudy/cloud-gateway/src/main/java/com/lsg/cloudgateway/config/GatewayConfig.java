package com.lsg.cloudgateway.config;

import com.lsg.cloudgateway.filter.CustGatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public CustGatewayFilter custFilter(){
        return new CustGatewayFilter();
    }
}
