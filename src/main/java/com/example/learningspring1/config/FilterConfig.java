package com.example.learningspring1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class FilterConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public FilterRegistrationBean<SessionValidationFilter2> sessionValidationFilter() {
        FilterRegistrationBean<SessionValidationFilter2> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionValidationFilter2(jdbcTemplate));
        registrationBean.addUrlPatterns("/*"); // Customize the URL pattern as needed
        registrationBean.setOrder(1); // Set the order if multiple filters are present
        return registrationBean;
    }
}
