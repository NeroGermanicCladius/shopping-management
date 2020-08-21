package com.example.configuration.web;

import com.example.configuration.security.PrincipalMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebServerConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new PrincipalMethodArgumentResolver());
            }
        };
    }
}
