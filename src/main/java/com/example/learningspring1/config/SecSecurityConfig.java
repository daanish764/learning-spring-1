package com.example.learningspring1.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
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

    @Bean
    public AuthenticationEntryPoint customEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        };
    }


    //
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        .maximumSessions(1) // Allow only one session per user
//                        .maxSessionsPreventsLogin(true) // Kick out the old session
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
               // .formLogin(withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Do nothing; just return a 200 status
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                )
                .httpBasic(withDefaults())
//                .securityContext(securityContext -> securityContext.requireExplicitSave(true)) // Prevent implicit security context storage
//                .requestCache(requestCache -> requestCache.disable()) // Disable request caching
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
