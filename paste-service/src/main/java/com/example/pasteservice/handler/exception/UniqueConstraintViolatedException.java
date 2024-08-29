package com.example.pasteservice.handler.exception;

public class UniqueConstraintViolatedException extends RuntimeException {

    public UniqueConstraintViolatedException(String message) {
        super(message);
    }

}