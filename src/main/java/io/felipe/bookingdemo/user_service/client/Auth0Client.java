package io.felipe.bookingdemo.user_service.client;

import io.felipe.bookingdemo.user_service.dto.TokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class Auth0Client {
    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;
    private final Logger logger = LoggerFactory.getLogger(Auth0Client.class);

    private final String auth0Domain = "https://dev-mcdamd0brvi77z7i.us.auth0.com/oauth/token";
    private final String clientId = "7nKk8YHYRoPRRz0tUtRxThK7xvfKqVuM";
    private final String clientSecret = "zrCwBwD7NH3PA0bqnMLcbuCO8QTTusRt79ZBc6qAlOhzHWqhJlL9rj-PksVUSPW8";
    private final String audience = "https://bookingdemo.felipe.io/api/auth/";

    public Auth0Client(RestTemplateBuilder restTemplateBuilder, RetryTemplate retryTemplate) {
        this.restTemplate = restTemplateBuilder.build();
        this.retryTemplate = retryTemplate;
    }

    public TokenDTO getAuthToken(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "password");
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", audience);
        body.put("username", username);
        body.put("password", password);
        body.put("scope", "openid profile email offline_access");

        try {
            return retryTemplate.execute(context -> {
                logger.info("Attempting to call Auth0 (attempt #{})", context.getRetryCount() + 1);

                HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
                ResponseEntity<TokenDTO> response = restTemplate.exchange(
                        auth0Domain, HttpMethod.POST, requestEntity, TokenDTO.class
                );

                return response.getBody();
            });
        } catch (HttpClientErrorException ex) {
            logger.warn("Failed login attempt for user: {}", username);
            throw new BadCredentialsException("Invalid login credentials");
        } catch (Exception ex) {
            logger.error("Error connecting to Auth0 for user: {}", username, ex);
            throw new AuthenticationServiceException("Error connecting to Auth0", ex);
        }
    }
}
