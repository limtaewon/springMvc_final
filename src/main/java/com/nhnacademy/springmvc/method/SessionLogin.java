package com.nhnacademy.springmvc.method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionLogin {

    private SessionLogin() {
    }

    public static String getSessionLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("login");
    }
}
