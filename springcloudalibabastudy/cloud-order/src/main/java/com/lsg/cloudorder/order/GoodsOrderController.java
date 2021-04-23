package com.lsg.cloudorder.order;

import cn.hutool.core.util.IdUtil;
import com.lsg.cloudapi.user.AccountClient;
import com.lsg.cloudapi.user.bean.CheckUidParam;
import com.lsg.cloudcommon.result.Result;
import com.lsg.cloudcommon.result.ResultCode;
import com.lsg.cloudorder.config.WeChatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/order")
public class GoodsOrderController {
    @Resource
    private AccountClient accountClient;
    @Autowired
    private WeChatConfig weChatConfig;

    @GetMapping("/createOrder")
    public Result createOrder(String uid, String goodsId) {
        // 验证用户UID
        Result checkUidResult = accountClient.checkUid(new CheckUidParam(uid));
        if (!checkUidResult.getCode().equals(ResultCode.OK.getCode())) {
            return checkUidResult;
        }
        // 验证商品goodsID

        return new Result(IdUtil.simpleUUID());
    }

    @GetMapping("/hello")
    public String hello() {
        return weChatConfig.toString();
    }


}
