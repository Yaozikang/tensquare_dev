package com.tensquare.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 要子康
 * @description SmsApplication
 * @since 2020/1/14 10:09
 */
@SpringBootApplication
@EnableEurekaClient
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
