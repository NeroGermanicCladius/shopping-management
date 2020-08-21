package com.example;

import com.example.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class ShoppingManagement {
    public static void main(final String[] args) {
        SpringApplication.run(ShoppingManagement.class, args);
    }
}
