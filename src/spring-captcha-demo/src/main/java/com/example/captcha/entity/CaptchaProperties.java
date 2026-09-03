package com.example.captcha.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "captcha")
@Data
public class CaptchaProperties {
    private Integer width;
    private Integer height;
    private Integer codeNum;
    private Integer lineCount;

    private Session session;
}
