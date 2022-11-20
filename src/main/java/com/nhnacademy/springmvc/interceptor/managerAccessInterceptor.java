package com.nhnacademy.springmvc.interceptor;

import com.nhnacademy.springmvc.domain.Customer;
import com.nhnacademy.springmvc.domain.User;
import com.nhnacademy.springmvc.exception.NotAllowAccessException;
import com.nhnacademy.springmvc.repository.UserRepository;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.nhnacademy.springmvc.method.NotFound.userNotFoundCheck;
import static com.nhnacademy.springmvc.method.SessionLogin.getSessionLogin;

public class managerAccessInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    public managerAccessInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginID = getSessionLogin(request);

        if(Objects.isNull(loginID)){
            response.sendRedirect("/cs/login");
        }

        User user = userRepository.getUser(loginID);
        userNotFoundCheck(user);

        if (user instanceof Customer) {
            throw new NotAllowAccessException("Customer");
        }

        return true;
    }
}
