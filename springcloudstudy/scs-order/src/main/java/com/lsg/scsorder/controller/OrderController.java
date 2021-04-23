package com.lsg.scsorder.controller;

import com.lsg.scsapi.payment.PayClient;
import com.lsg.scsapi.payment.bean.PayParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private PayClient payClient;

    @GetMapping("/createOrder")
    public String createOrder(String uid, Integer money) {
        log.info("当前线程：{}", Thread.currentThread().getName());
        String result = payClient.payMoney(new PayParam(uid, money));
        System.out.println("result = " + result);
        if (!"支付成功！！！".equals(result)){
            return result;
        }
        System.out.println("用户：" + uid + "创建订单成功！！！");
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
