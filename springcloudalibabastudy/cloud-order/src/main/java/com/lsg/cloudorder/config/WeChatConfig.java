package com.lsg.cloudorder.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
public class WeChatConfig {

    @Value("${wechat.authorize.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${wechat.authorize.accessTokenGrantType}")
    private String accessTokenGrantType;
}
