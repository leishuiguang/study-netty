package com.lsg.scseureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ScsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScsEurekaApplication.class, args);
	}

}
