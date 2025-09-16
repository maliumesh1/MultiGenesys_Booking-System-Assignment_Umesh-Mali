package com.company.multigenesys_bookingsystemassignment_umeshmali.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import com.company.multigenesys_bookingsystemassignment_umeshmali.util.JwtUtil;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(@Value("${jwt.secret:replace-me-with-a-very-long-secret-key-at-least-32-chars}") String secret) {
        return new JwtUtil(secret);
    }
}
