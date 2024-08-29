package com.example.userservice.dto;

import lombok.Builder;

@Builder
public record GetUserDto(String username,
                         String email) {
}
