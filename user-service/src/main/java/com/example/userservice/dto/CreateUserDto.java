package com.example.userservice.dto;

import com.example.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUserDto(@NotBlank
                            @Length(min = 3, max = 20, message = "Username must be longer then 3 and shorter then 20 symbols")
                            String username,

                            @NotBlank
                            @Length(min = 6, max = 20, message = "Password must be longer then 6 and shorter then 20 symbols")
                            String password,

                            @Email
                            String email) {
    public User toUser() {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .build();
    }
}