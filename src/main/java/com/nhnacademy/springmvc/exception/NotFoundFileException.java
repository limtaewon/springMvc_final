package com.nhnacademy.springmvc.exception;

public class NotFoundFileException extends RuntimeException {
    public NotFoundFileException(String message) {
        super(message);
    }
}
