package com.example.ezimenu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/signup")
                .excludePathPatterns("/eatery/{id}/categories")
                .excludePathPatterns("/category/{id}")
                .excludePathPatterns("/eatery/{id}/dishes")
                .excludePathPatterns("/category/{id}/dishes")
                .excludePathPatterns("/dish/{id}")
                .excludePathPatterns("/table/{id}/orders/{status}")
                .excludePathPatterns("/guest/**");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
