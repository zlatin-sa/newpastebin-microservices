package com.example.userservice.handler;

import com.example.userservice.handler.exception.ResourceNotFoundException;
import com.example.userservice.handler.exception.UniqueConstraintViolatedException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError catchUniqueConstraintViolatedException(UniqueConstraintViolatedException e) {
        log.error(e.getMessage(), e);
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppError catchConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}