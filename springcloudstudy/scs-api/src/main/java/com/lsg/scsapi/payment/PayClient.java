package com.lsg.scsapi.payment;

import com.lsg.scsapi.payment.bean.PayParam;
import com.lsg.scsapi.payment.fallback.PayClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "scs-payment", contextId = "PayClient", fallbackFactory = PayClientFallbackFactory.class)
public interface PayClient {

    @PostMapping("/pay/payMoney")
    String payMoney(@RequestBody PayParam param);


}
