package io.felipe.bookingdemo.user_service.service;

import io.felipe.bookingdemo.user_service.dto.UserDTO;
import io.felipe.bookingdemo.user_service.entity.User;
import io.felipe.bookingdemo.user_service.repository.UserRepository;
import io.felipe.bookingdemo.user_service.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserMapper userMapper = mock(UserMapper.class);

    private final UserService userService = new UserService(userMapper, userRepository, passwordEncoder);

    private static final String ID = "1";
    private static final String PASSWORD = "Passw0rd!";
    private static final String ROLE = "ADMIN";
    private static final String NAME = "Felipe Fernandes Diogo";
    private static final String EMAIL = "felipe@example.com";

    @Test
    @DisplayName("Should return user when found")
    void shouldReturnUserWhenFound() {
        User mockedUser = buildUser1();
        UserDTO mockedUserDTO = buildUserDTO1();

        when(userRepository.findById(ID)).thenReturn(Optional.of(mockedUser));
        when(userMapper.toDTO(mockedUser)).thenReturn(mockedUserDTO);

        assertEquals(mockedUserDTO, userService.getUser(ID));
    }

    private User buildUser1() {
        User user = new User();
        user.setBirthDate(Date.valueOf("1990-06-10"));
        user.setName(NAME);
        user.setId(ID);
        user.setRole(ROLE);
        user.setEmail(EMAIL);
        user.setUpdatedAt(Instant.now());
        user.setCreatedAt(Instant.now());
        user.setPassword(PASSWORD);
        return user;
    }

    private UserDTO buildUserDTO1() {
        UserDTO userDTO = new UserDTO();
        userDTO.setBirthDate(LocalDate.of(1990,6, 10));
        userDTO.setName(NAME);
        userDTO.setId(ID);
        userDTO.setRole(ROLE);
        userDTO.setEmail(EMAIL);
        userDTO.setUpdatedAt(OffsetDateTime.now());
        userDTO.setCreatedAt(OffsetDateTime.now());
        userDTO.setPassword(PASSWORD);
        return userDTO;
    }
}
