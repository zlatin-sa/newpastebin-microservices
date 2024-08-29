package com.example.userservice.handler.exception;

public class UniqueConstraintViolatedException extends RuntimeException {

    public UniqueConstraintViolatedException(String message) {
        super(message);
    }

}