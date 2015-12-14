package com.recursivechaos.johnny5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Johnny5Application {

    public static void main(String[] args) {
        SpringApplication.run(Johnny5Application.class, args);
    }
}
