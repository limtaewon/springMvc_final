package com.nhnacademy.springmvc.exception;

public class NotExistUserException extends RuntimeException {
    public NotExistUserException() {
        super("등록되어 있지 않은 사용자입니다");
    }
}
