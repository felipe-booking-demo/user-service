package io.felipe.bookingdemo.user_service.service;

import io.felipe.bookingdemo.user_service.entity.User;
import io.felipe.bookingdemo.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }
}
