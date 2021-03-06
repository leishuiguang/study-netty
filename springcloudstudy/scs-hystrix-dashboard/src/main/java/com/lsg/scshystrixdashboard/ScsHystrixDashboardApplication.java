package com.lsg.scshystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
//@EnableCircuitBreaker
public class ScsHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScsHystrixDashboardApplication.class, args);
    }

}
