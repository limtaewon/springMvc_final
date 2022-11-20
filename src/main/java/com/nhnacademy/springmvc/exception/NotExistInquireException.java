package com.nhnacademy.springmvc.exception;

public class NotExistInquireException extends RuntimeException {
    public NotExistInquireException() {
        super("등록되어 있지 않은 문의번호입니다.");
    }
}
