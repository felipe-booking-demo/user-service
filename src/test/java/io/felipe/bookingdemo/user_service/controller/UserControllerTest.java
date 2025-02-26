package io.felipe.bookingdemo.user_service.controller;

import io.felipe.bookingdemo.user_service.config.SecurityConfigTest;
import io.felipe.bookingdemo.user_service.dto.UserDTO;
import io.felipe.bookingdemo.user_service.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfigTest.class)
public class UserControllerTest {
    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private static final String ID = "1";
    private static final String PASSWORD = "Passw0rd!";
    private static final String USERNAME = "usern4am3";
    private static final String ROLE = "ADMIN";
    private static final String NAME = "Felipe Fernandes Diogo";
    private static final String EMAIL = "felipe@example.com";
    private static final String BIRTHDATE = "1990-06-10";

    @Test
    @DisplayName("Should return user by id")
    void shouldReturnUserById() throws Exception {
        UserDTO expected = buildUserDTO1();
        when(userService.getUser(ID)).thenReturn(expected);

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
        verify(userService).getUser(ID);
    }

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() throws Exception {
        whenCallingGetUsersReturnListOfUsers();

        mockMvc.perform(
                        get("/api/users").contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(NAME))
                .andExpect(jsonPath("$[0].password").value(PASSWORD))
                .andExpect(jsonPath("$[0].username").value(USERNAME))
                .andExpect(jsonPath("$[0].role").value(ROLE))
                .andExpect(jsonPath("$[0].email").value(EMAIL))
                .andExpect(jsonPath("$[0].birthDate").value(BIRTHDATE));
        verify(userService).getUsers();
    }

    @Test
    @DisplayName("Should save user")
    void shouldSaveUser() throws Exception {
        UserDTO expected = buildUserDTO1();
        expected.setId(null);
        assertNull(expected.getId());

        when(userService.saveUser(any(UserDTO.class))).thenAnswer(savedUser -> {
            System.out.print(savedUser);
            expected.setId(ID);
            return expected;
        });

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                                {
                                    "name": "%s",
                                    "password": "%s",
                                    "username": "%s",
                                    "role": "%s",
                                    "email": "%s",
                                    "birthDate": "%s"
                                }
                                """, NAME, PASSWORD, USERNAME, ROLE, EMAIL, BIRTHDATE)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.password").value(PASSWORD))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.role").value(ROLE))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.birthDate").value("1990-06-10"));
    }

    private void whenCallingGetUsersReturnListOfUsers() {
        UserDTO expected = buildUserDTO1();
        ArrayList<UserDTO> users = new ArrayList<>();
        users.add(expected);

        when(userService.getUsers()).thenReturn(users);
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
}
