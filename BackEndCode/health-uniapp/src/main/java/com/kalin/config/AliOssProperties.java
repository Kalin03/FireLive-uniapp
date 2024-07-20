package com.kalin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "aliyun.oss")
@Component
public class AliOssProperties {

    private String ENDPOINT;

    private String ACCESS_KEY_ID;

    private String ACCESS_KEY_SECRET;

    private String BUCKET_NAME;
}
