package com.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/api/v1.*"))
                .build()
                .securitySchemes(Collections.singletonList(new BasicAuth("basicAuth")));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Shopping Management API")
                .description("Shopping Management API reference for developers")
                .version("1.0.0")
                .build();
    }
}
