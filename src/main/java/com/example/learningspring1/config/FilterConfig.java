package com.example.learningspring1.config;

import com.example.learningspring1.SessionValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SessionValidationFilter> sessionValidationFilter() {
        FilterRegistrationBean<SessionValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionValidationFilter());
        registrationBean.addUrlPatterns("/*"); // Customize the URL pattern as needed
        registrationBean.setOrder(1); // Set the order if multiple filters are present
        return registrationBean;
    }
}
