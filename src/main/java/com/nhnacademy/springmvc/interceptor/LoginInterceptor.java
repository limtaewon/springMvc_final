package com.nhnacademy.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.nhnacademy.springmvc.method.SessionLogin.getSessionLogin;


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String login = getSessionLogin(request);

        if (Objects.isNull(login)) {
            response.sendRedirect("/cs/login");
            return false;
        }
        return true;
    }

}
