package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Customer;
import com.nhnacademy.springmvc.domain.User;
import com.nhnacademy.springmvc.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.nhnacademy.springmvc.method.NotFound.userNotFoundCheck;
import static com.nhnacademy.springmvc.method.SessionLogin.getSessionLogin;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/cs/login")
    public String getLogin(HttpServletRequest req) {
        String loginID = getSessionLogin(req);

        if (Objects.isNull(loginID)) {
            return "loginForm";
        }

        User user = userRepository.getUser(loginID);
        userNotFoundCheck(user);

        return accordingTypeForRedirect(user);
    }

    @PostMapping("/cs/login")
    public String postLogin(@RequestParam String id,
                            @RequestParam String password,
                            HttpServletRequest req) {
        HttpSession session = req.getSession();

        if (userRepository.matches(id, password)) {
            session.setAttribute("login", id);
            User user = userRepository.getUser(id);

            return accordingTypeForRedirect(user);
        }

        return "loginForm";
    }

    private String accordingTypeForRedirect(User user) {
        if (user instanceof Customer) {
            return "redirect:/cs";
        }
        return "redirect:/cs/admin";
    }
}
