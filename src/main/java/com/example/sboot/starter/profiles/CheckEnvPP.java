package com.example.sboot.starter.profiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class CheckEnvPP implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String os = System.getProperty("os.name");
        if (os.equalsIgnoreCase("Linux")) {
            environment.addActiveProfile("dev");
        }
    }
}
