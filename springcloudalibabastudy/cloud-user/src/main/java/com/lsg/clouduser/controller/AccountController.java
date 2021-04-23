package com.lsg.clouduser.controller;

import com.lsg.cloudapi.user.AccountClient;
import com.lsg.cloudapi.user.bean.CheckUidParam;
import com.lsg.cloudcommon.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AccountController implements AccountClient {

    @Override
    public Result checkUid(CheckUidParam param) {
        log.info("用户信息：{}", param);
        return Result.success();
    }
}
