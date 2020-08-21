package com.example.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "shop")
public class ApplicationProperties {

    private SecurityProperties security;

    @Data
    public static class SecurityProperties {

        private String authTokenKey;
        private String jwtSecret;
        private long expirationMillis;
        private String corsAllowAllPattern;
    }
}
