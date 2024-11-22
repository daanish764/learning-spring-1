package com.example.learningspring1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN;


@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

    @Autowired
    DataSource dataSource;


    // Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }


    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        MyCustomDaoAuthenticationProvider authenticationProvider = new MyCustomDaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }



    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);

        UserDetails admin2 = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();


        jdbcUserDetailsManager.deleteUser(admin2.getUsername());

        if(!jdbcUserDetailsManager.userExists(admin2.getUsername())) {
            jdbcUserDetailsManager.createUser(admin2);
        }

        return jdbcUserDetailsManager;
    }

    //
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .headers(headers -> headers.referrerPolicy(referrer -> referrer.policy(SAME_ORIGIN)));

        return http.build();
    }
    //
    @Bean
    public static PasswordEncoder passwordEncoder() {
        CharSequence secretKey = "secret-key";

        Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm algorithm =
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256; // Algorithm


        // Configure Argon2PasswordEncoder with custom parameters if needed
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder(
                secretKey, // Secret or salt value (keep it secure)
                65536,        // Iteration count
                128           // Hash width
                , algorithm
        );

        encoder.setEncodeHashAsBase64(false);

        return encoder;
    }

    // Custom failure handler for authentication failures
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(401); // Set status as Unauthorized
            response.getWriter().write("{\"error\": \"Invalid username or password\"}"); // Return error message
        };
    }

}
