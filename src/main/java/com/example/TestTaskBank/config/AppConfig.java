package com.example.TestTaskBank.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

public class AppConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}

