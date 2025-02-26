package io.felipe.bookingdemo.user_service.mapper;

import io.felipe.bookingdemo.user_service.dto.UserDTO;
import io.felipe.bookingdemo.user_service.entity.User;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class UserMapper {
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setBirthDate(userDTO.getBirthDate() != null ? DateMapper.toDate(userDTO.getBirthDate()) : null);
        user.setCreatedAt(userDTO.getCreatedAt() != null ? userDTO.getCreatedAt().toInstant() : null);
        user.setUpdatedAt(userDTO.getUpdatedAt() != null ? userDTO.getUpdatedAt().toInstant() : null);
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setBirthDate(user.getBirthDate() != null ? DateMapper.toLocalDate(user.getBirthDate()) : null);
        userDTO.setCreatedAt(user.getCreatedAt() != null ? user.getCreatedAt().atOffset(ZoneOffset.UTC) : null);
        userDTO.setUpdatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().atOffset(ZoneOffset.UTC) : null);
        return userDTO;
    }
}
