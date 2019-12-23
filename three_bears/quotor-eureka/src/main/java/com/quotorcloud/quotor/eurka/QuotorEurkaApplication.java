package com.quotorcloud.quotor.eurka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class QuotorEurkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotorEurkaApplication.class, args);
    }

}
