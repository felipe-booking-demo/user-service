package io.felipe.bookingdemo.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

public class UserDTO {
    private String id;
    @NotBlank(message = "Field required")
    private String email;
    @NotBlank(message = "Field required")
    private String name;
    @NotBlank(message = "Field required")
    private String username;
    @NotBlank(message = "Field required")
    private String password;
    private String role;
    private LocalDate birthDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public UserDTO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserDTO userDTO)) return false;

        return new EqualsBuilder().append(id, userDTO.id).append(email, userDTO.email).append(name, userDTO.name).append(username, userDTO.username).append(password, userDTO.password).append(role, userDTO.role).append(birthDate, userDTO.birthDate).append(createdAt, userDTO.createdAt).append(updatedAt, userDTO.updatedAt).isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, username, password, role, birthDate, createdAt, updatedAt);
    }
}
