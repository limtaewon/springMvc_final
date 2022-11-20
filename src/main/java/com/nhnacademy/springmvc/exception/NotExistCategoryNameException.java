package com.nhnacademy.springmvc.exception;

public class NotExistCategoryNameException extends RuntimeException {
    public NotExistCategoryNameException(String cause) {
        super(cause);
    }
}
