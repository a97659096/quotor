package com.quotorcloud.quotor.auth;

import com.quotorcloud.quotor.common.security.annotation.EnablePigFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnablePigFeignClients
public class QuotorAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotorAuthApplication.class, args);
    }

}
