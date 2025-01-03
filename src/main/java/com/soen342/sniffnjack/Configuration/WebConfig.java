package com.soen342.sniffnjack.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.lang.NonNull;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // Allow only requests from your frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")  // Allow specific HTTP methods
                .allowCredentials(true);  // Allow credentials if necessary
    }
}
