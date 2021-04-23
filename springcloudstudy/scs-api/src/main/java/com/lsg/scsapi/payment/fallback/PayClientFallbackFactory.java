package com.lsg.scsapi.payment.fallback;

import com.lsg.scsapi.payment.PayClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PayClientFallbackFactory implements FallbackFactory<PayClient> {

    @Override
    public PayClient create(Throwable cause) {
        return param -> "支付服务异常，异常信息：" + cause.getMessage();
    }
}
