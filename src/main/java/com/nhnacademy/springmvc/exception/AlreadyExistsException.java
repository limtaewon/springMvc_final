package com.nhnacademy.springmvc.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super("이미 존재하는 id입니다 : " + message);
    }
}
