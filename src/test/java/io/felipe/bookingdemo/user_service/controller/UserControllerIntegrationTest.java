package io.felipe.bookingdemo.user_service.controller;

import io.felipe.bookingdemo.user_service.config.SecurityConfigTest;
import io.felipe.bookingdemo.user_service.dto.UserDTO;
import io.felipe.bookingdemo.user_service.entity.User;
import io.felipe.bookingdemo.user_service.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfigTest.class)
public class UserControllerIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private static final String ID = "1";
    private static final String PASSWORD = "Passw0rd!";
    private static final String USERNAME = "usern4am3";
    private static final String ROLE = "ADMIN";
    private static final String NAME = "Felipe Fernandes Diogo";
    private static final String EMAIL = "felipe@example.com";
    private static final String BIRTHDATE = "1990-06-10";
    private static final OffsetDateTime DATE_TIME_NOW = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Instant INSTANT_NOW = DATE_TIME_NOW.toInstant();

    @Test
    @DisplayName("Should return user by id - Integration tests")
    void shouldReturnUserById() throws Exception {
        when(userRepository.findById(ID)).thenReturn(Optional.of(buildUser1()));

        mockMvc.perform(
                        get("/api/users/{id}", ID).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.password").value(PASSWORD))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.role").value(ROLE))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.birthDate").value(BIRTHDATE));

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
        userDTO.setUsername(USERNAME);
        userDTO.setPassword(PASSWORD);
        return userDTO;
    }

    private User buildUser1() {
        User user = new User();
        LocalDate localDate = LocalDate.of(1990, 6, 10);  // YYYY, MM, DD
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setBirthDate(date);
        user.setName(NAME);
        user.setId(ID);
        user.setRole(ROLE);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setUpdatedAt(INSTANT_NOW);
        user.setCreatedAt(INSTANT_NOW);
        user.setPassword(PASSWORD);
        return user;
    }

}
