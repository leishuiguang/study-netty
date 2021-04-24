package com.lsg.cloudpayment;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
public class CloudPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudPaymentApplication.class, args);
	}

	@RabbitListener(queues = "test.news")
	public void listen(Object o) {
		System.out.println(o);
	}

}
