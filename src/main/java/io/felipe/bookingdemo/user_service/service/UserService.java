package io.felipe.bookingdemo.user_service.service;

import io.felipe.bookingdemo.user_service.dto.UserDTO;
import io.felipe.bookingdemo.user_service.entity.User;
import io.felipe.bookingdemo.user_service.exception.ResourceNotFoundException;
import io.felipe.bookingdemo.user_service.mapper.UserMapper;
import io.felipe.bookingdemo.user_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getUsers() {
        return this.userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    public UserDTO saveUser(UserDTO userDTO) throws IllegalArgumentException {

        if (userDTO.getId() != null) {
            throw new IllegalArgumentException("ID should not be provided for new users");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setCreatedAt(Instant.now().atOffset(ZoneOffset.UTC));
        userDTO.setUpdatedAt(Instant.now().atOffset(ZoneOffset.UTC));

        User user = this.userRepository.save(userMapper.toEntity(userDTO));
        return userMapper.toDTO(user);
    }

    public UserDTO updateUser(String id, UserDTO userDTO) throws IllegalArgumentException, ResourceNotFoundException {
        checkUserIdThrowsExceptionIfNot(id, userDTO);

        userDTO.setUpdatedAt(Instant.now().atOffset(ZoneOffset.UTC));

        User updatedUser = userRepository.save(userMapper.toEntity(userDTO));
        return userMapper.toDTO(updatedUser);
    }

    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }

    public UserDTO getUser(String id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .map(userMapper::toDTO).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private static void checkUserIdThrowsExceptionIfNot(String id, UserDTO userDTO) throws IllegalArgumentException {
        if (!id.equals(userDTO.getId())) {
            throw new IllegalArgumentException("something");
        }
    }
}
