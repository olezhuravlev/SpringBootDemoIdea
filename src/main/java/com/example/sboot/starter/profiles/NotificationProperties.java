package com.example.sboot.starter.profiles;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "prod")
public class NotificationProperties {
    private List<String> mails;
    private boolean canNotify;
}

