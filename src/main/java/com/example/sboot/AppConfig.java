package com.example.sboot;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@Slf4j
@EnableCaching
public class AppConfig {

    /*
    @Profile("!test")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }
    */

    // To have ability to connect to H2 from outside by URL
    // jdbc:h2:tcp://localhost:9092/mem:voting
    @Bean(initMethod = "start", destroyMethod = "stop")
    Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}
