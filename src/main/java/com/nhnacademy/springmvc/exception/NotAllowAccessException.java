package com.nhnacademy.springmvc.exception;

public class NotAllowAccessException extends RuntimeException {
    public NotAllowAccessException(String message) {
        super(message + "는(은) 해당페이지에 접근권한이 없습니다.");
    }
}
