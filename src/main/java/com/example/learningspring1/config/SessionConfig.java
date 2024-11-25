package com.example.learningspring1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.sql.DataSource;

@Configuration
@EnableJdbcHttpSession(tableName = "SPRING_SESSION", maxInactiveIntervalInSeconds = 1800) // 30-minute timeout
public class SessionConfig {



    // If you need a custom DataSource for Spring Session, define it here
    // Otherwise, Spring will use the primary DataSource bean from your application context
    @SpringSessionDataSource
    public DataSource springSessionDataSource(DataSource dataSource) {
        return dataSource; // Reuse the application's primary DataSource
    }


}
