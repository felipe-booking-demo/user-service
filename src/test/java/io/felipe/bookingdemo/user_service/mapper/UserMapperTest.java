package io.felipe.bookingdemo.user_service.mapper;

import io.felipe.bookingdemo.user_service.dto.UserDTO;
import io.felipe.bookingdemo.user_service.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();

    private static final String ID = "1";
    private static final String PASSWORD = "Passw0rd!";
    private static final String ROLE = "ADMIN";
    private static final String NAME = "Felipe Fernandes Diogo";
    private static final String EMAIL = "felipe@example.com";
    private static final OffsetDateTime dateTimeNow = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Instant instantNow = dateTimeNow.toInstant();

    @Test
    @DisplayName("Should convert DTO to Entity")
    void shouldConvertDTOToEntity() {
        User user = buildUser1();
        UserDTO userDTO = buildUserDTO1();

        User result = userMapper.toEntity(userDTO);

        assertThat(user).isEqualTo(result);
        assertThat(user.hashCode()).isEqualTo(result.hashCode());
        assertThat(user.toString()).isEqualTo(result.toString());

        // Test date as nulls as well
        user.setCreatedAt(null);
        user.setUpdatedAt(null);
        user.setBirthDate(null);
        userDTO.setCreatedAt(null);
        userDTO.setUpdatedAt(null);
        userDTO.setBirthDate(null);

        assertThat(user).isEqualTo(userMapper.toEntity(userDTO));
        assertThat(user).isEqualTo(user);
    }

    @Test
    @DisplayName("Should fail equals assertion between DTO and Entity")
    void shouldNotBeEqualDtoToEntity() {
        User user = buildUser1();
        UserDTO userDTO = buildUserDTO1();

        assertThat(user).isNotEqualTo(userDTO);
        assertThat(userDTO).isNotEqualTo(user);

    }

    @Test
    @DisplayName("Should convert DTO to Entity")
    void shouldConvertEntityToDTO() {
        User user = buildUser1();
        UserDTO userDTO = buildUserDTO1();

        UserDTO result = userMapper.toDTO(user);

        assertThat(userDTO).isEqualTo(result);
        assertThat(userDTO.hashCode()).isEqualTo(result.hashCode());
        assertThat(userDTO.toString()).isEqualTo(result.toString());

        // Test date as nulls as well
        user.setCreatedAt(null);
        user.setUpdatedAt(null);
        user.setBirthDate(null);
        userDTO.setCreatedAt(null);
        userDTO.setUpdatedAt(null);
        userDTO.setBirthDate(null);

        assertThat(userDTO).isEqualTo(userMapper.toDTO(user));
    }

    private User buildUser1() {
        User user = new User();
        LocalDate localDate = LocalDate.of(1990, 6, 10);  // YYYY, MM, DD
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setBirthDate(date);
        user.setName(NAME);
        user.setId(ID);
        user.setRole(ROLE);
        user.setEmail(EMAIL);
        user.setUpdatedAt(instantNow);
        user.setCreatedAt(instantNow);
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
        userDTO.setUpdatedAt(dateTimeNow);
        userDTO.setCreatedAt(dateTimeNow);
        userDTO.setPassword(PASSWORD);
        return userDTO;
    }
}
