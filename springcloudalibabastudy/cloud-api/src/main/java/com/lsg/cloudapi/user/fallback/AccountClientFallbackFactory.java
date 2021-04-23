package com.lsg.cloudapi.user.fallback;

import com.lsg.cloudapi.user.AccountClient;
import com.lsg.cloudcommon.result.Result;
import com.lsg.cloudcommon.result.ResultCode;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountClientFallbackFactory implements FallbackFactory<AccountClient> {

    @Override
    public AccountClient create(Throwable throwable) {
        log.error("服务调用异常，异常信息：{}", throwable.getMessage());
        return param -> Result.failure(ResultCode.SERVICE_ERROR);
    }
}
