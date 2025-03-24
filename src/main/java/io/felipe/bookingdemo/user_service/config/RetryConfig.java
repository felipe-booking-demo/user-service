package io.felipe.bookingdemo.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.nio.channels.UnresolvedAddressException;

@Configuration
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(3)
                .fixedBackoff(2000)
                .retryOn(ResourceAccessException.class) // network errors
                .retryOn(HttpServerErrorException.class) // 5xx responses
                .retryOn(UnresolvedAddressException.class) // your specific case
                .build();
    }

}
