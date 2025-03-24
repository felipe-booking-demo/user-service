package io.felipe.bookingdemo.user_service.service;

import io.felipe.bookingdemo.user_service.client.Auth0Client;
import io.felipe.bookingdemo.user_service.dto.LoginDTO;
import io.felipe.bookingdemo.user_service.dto.TokenDTO;
import io.felipe.bookingdemo.user_service.entity.User;
import io.felipe.bookingdemo.user_service.exception.ResourceNotFoundException;
import io.felipe.bookingdemo.user_service.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Auth0Client auth0Client;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, Auth0Client auth0Client) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.auth0Client = auth0Client;
    }

    public TokenDTO authenticate(LoginDTO loginDTO) {
        User user = this.userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(
                () -> new BadCredentialsException("Either the password or the username is incorrect")
        );

        if (!isPasswordMatch(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Either the password or the username is incorrect");
        }

        return this.auth0Client.getAuthToken(loginDTO.getUsername(), loginDTO.getPassword());
    }

    public boolean isPasswordMatch(String passwordToCheck, String userPassword) {
        return this.passwordEncoder.matches(passwordToCheck, userPassword);
    }
}
