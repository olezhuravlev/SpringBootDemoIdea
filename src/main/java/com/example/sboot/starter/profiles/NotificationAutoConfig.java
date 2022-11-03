package com.example.sboot.starter.profiles;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
@EnableConfigurationProperties(NotificationProperties.class)
public class NotificationAutoConfig {

    @Bean
    @Profile("prod")
    //@ConditionalOnMissingBean(annotation = Exist.class)
    @ConditionalOnProperty(name = "prod.can-notify", matchIfMissing = true, havingValue = "true")
    public ApplicationListener notificationListener(NotificationProperties notificationProperties) {
        return new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                notificationProperties.getMails().forEach(System.out::println);
            }
        };
    }
}
