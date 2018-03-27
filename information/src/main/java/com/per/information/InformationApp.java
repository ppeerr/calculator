package com.per.information;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InformationApp {

    public static void main(String[] args) {
        SpringApplication.run(InformationApp.class, args);
    }
}
