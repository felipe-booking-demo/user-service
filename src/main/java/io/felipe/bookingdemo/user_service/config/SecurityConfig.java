package io.felipe.bookingdemo.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: This method is disabling security in all requests, needs to be removed in next iteration - Felipe, 2025-02-24
    // I've created an application in Auth0 - documentation for the application here:
    //  https://www.notion.so/Authentication-Auth0-1ab7c6f54aa180e68925db11b2e82750?pvs=4
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  // ✅ New recommended syntax
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
