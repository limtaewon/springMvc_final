package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler(value = {
            NotFoundFileException.class,
            NotImageFileException.class,
            NotAllowAccessException.class,
            AlreadyExistsException.class,
            NotExistInquireException.class,
            NotExistUserException.class,
            NotExistCategoryNameException.class,
            Exception.class})
    public String handleValidationException(Exception ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);

        return "error";
    }
}
