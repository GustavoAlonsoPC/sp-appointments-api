package com.appointments.spappoitmentsapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/*
 * Config Swagger docs
 * */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiDetails() {
        return new ApiInfo("SP Medical Appointments API REST",
                "Medical Appointments API rest docs",
                "1.0",
                "https://www.google.com",
                new Contact("Gustavo Peñaranda", "https://github.com/TNTtato", "penarandagustavo5@gmail.com"),
                "MIT",
                "https://www.google.com",
                Collections.emptyList());
    }
}
