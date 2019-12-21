package com.quotorcloud.quotor.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class QuotorConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotorConfigApplication.class, args);
    }

}
