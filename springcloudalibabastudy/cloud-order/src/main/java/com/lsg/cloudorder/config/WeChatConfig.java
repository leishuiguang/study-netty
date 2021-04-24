package com.lsg.cloudorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "wechat.authorize")
public class WeChatConfig {

    private String accessTokenUrl;

    private String accessTokenGrantType;
}
