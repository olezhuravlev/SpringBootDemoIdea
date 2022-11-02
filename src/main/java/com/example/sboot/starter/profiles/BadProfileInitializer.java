package com.example.sboot.starter.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class BadProfileInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    // Won't work.
    // @Autowired
    // ApplicationContext applicationContext;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (applicationContext.getEnvironment().getActiveProfiles().length == 0) {
            throw new RuntimeException("Should have production profile");
        }
    }
}
