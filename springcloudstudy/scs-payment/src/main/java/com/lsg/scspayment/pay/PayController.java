package com.lsg.scspayment.pay;

import com.lsg.scsapi.payment.PayClient;
import com.lsg.scsapi.payment.bean.PayParam;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class PayController implements PayClient {


    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")//失败率多少 达到跳闸
            })
    @Override
    public String payMoney(PayParam param) {
        if (param.getMoney() < 0){
            throw new RuntimeException("金额不能为负数");
        }
//        try {
//            TimeUnit.MILLISECONDS.sleep(900);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("info:" + param);
        return "支付成功！！！";
    }
}
