package com.lsg.cloudapi.user;

import com.lsg.cloudapi.user.bean.CheckUidParam;
import com.lsg.cloudapi.user.fallback.AccountClientFallbackFactory;
import com.lsg.cloudcommon.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cloud-user", contextId = "accountClient", fallbackFactory = AccountClientFallbackFactory.class)
public interface AccountClient {

    /**
     * 验证用户UID
     *
     * @param param
     * @return
     */
    @PostMapping("/account/checkUid")
    Result checkUid(@RequestBody CheckUidParam param);
}
