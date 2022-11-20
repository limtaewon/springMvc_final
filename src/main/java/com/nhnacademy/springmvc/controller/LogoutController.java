package com.nhnacademy.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LogoutController {

    @GetMapping("/cs/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String loginID = (String) session.getAttribute("login");

        if (Objects.isNull(loginID)) {
            return "loginForm";
        }

        session.removeAttribute("login");

        return "redirect:/";
    }
}
