package com.example.sboot.starter.profiles;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NestedConditionalConfigs {

    @Bean
    @ConditionalOnClass({Bean1.class, Bean2.class})
    @ConditionalOnMissingBean({Bean3.class})
    public Dummy1 getDummy1() {
        return new Dummy1();
    }

    @Bean
    @ConditionalOnClass({Bean1.class})
    @ConditionalOnMissingBean({Bean2.class})
    public Dummy2 getDummy2() {
        return new Dummy2();
    }

    @Bean
    @ConditionalOnClass({Bean2.class})
    @ConditionalOnMissingBean({Bean3.class})
    public Dummy3 getDummy3() {
        return new Dummy3();
    }
}
