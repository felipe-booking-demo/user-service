package io.felipe.bookingdemo.user_service.config;

import io.felipe.bookingdemo.user_service.mapper.UserMapper;
import io.felipe.bookingdemo.user_service.repository.UserRepository;
import io.felipe.bookingdemo.user_service.service.UserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class SecurityConfigTest  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserRepository userRepository() {
        return mock(UserRepository.class);  // ✅ Manually mock UserRepository
    }

    // TODO: This method is disabling security in all requests, needs to be removed in next iteration - Felipe, 2025-02-24
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

    // Creating bean for User Service, so integration tests can be done, the only thing that will be ignored is the repository.
    @Bean
    public UserService userService() {
        UserService userService = new UserService(new UserMapper(), this.userRepository(), this.passwordEncoder());

        return userService;
    }

}
