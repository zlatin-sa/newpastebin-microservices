package com.example.userservice.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppError {
    private Integer statusCode;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();

    public AppError(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}