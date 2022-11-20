package com.nhnacademy.springmvc.method;

import com.nhnacademy.springmvc.domain.User;
import com.nhnacademy.springmvc.exception.NotExistUserException;

import java.util.Objects;

public class NotFound {

    public static void userNotFoundCheck(User user) {
        if(Objects.isNull(user)){
            throw new NotExistUserException();
        }
    }
}
