package com.lsg.scsorder;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.lsg")
@EnableDiscoveryClient
@EnableFeignClients("com.lsg")
@EnableCircuitBreaker
public class ScsOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScsOrderApplication.class, args);
    }

    /**
     * 此配置是为了服务监控而配置,与服务容错本身无关,spring cloud升级后的坑
     * ServletRegistrationBean因为spring boot的默认路径不是"/hystrix.stream",
     * 只要在自己的项目配置上下面的servlet就可以了
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}
