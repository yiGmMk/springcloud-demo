package com.xwb.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

// Spring Security的基础配置类
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        /*
         * BCryptPasswordEncoder：使用bcrypt强哈希加密。
         * NoOpPasswordEncoder：不使用任何转码。
         * Pbkdf2PasswordEncoder：使用PBKDF2加密。
         * SCryptPasswordEncoder：使用Scrypt哈希加密。
         * StandardPasswordEncoder：使用SHA-256哈希加密。
         * 
         */
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/").hasAuthority("user")
                .anyExchange().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                // .oauth2Login()// oauth2
                // .and()
                .build();
    }
}