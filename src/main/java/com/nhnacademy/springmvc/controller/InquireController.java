package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.*;
import com.nhnacademy.springmvc.exception.NotExistInquireException;
import com.nhnacademy.springmvc.repository.AnswerRepository;
import com.nhnacademy.springmvc.repository.PostRepository;
import com.nhnacademy.springmvc.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.nhnacademy.springmvc.method.NotFound.userNotFoundCheck;
import static com.nhnacademy.springmvc.method.SessionLogin.getSessionLogin;

@Controller
public class InquireController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public InquireController(PostRepository postRepository,
                             UserRepository userRepository,
                             AnswerRepository answerRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/inquire/{inquireID}")
    public String inquireDetail(@PathVariable long inquireID,
                                Model model,
                                HttpServletRequest request) {
        Inquire inquire = postRepository.getInquire(inquireID);
        Answer answer = answerRepository.getAnswer(inquireID);

        if(Objects.isNull(inquire)){
            throw new NotExistInquireException();
        }

        model.addAttribute("inquire", inquire);
        model.addAttribute("answer", answer);

        userTypeCheck(model, request);

        return "inquireDetail";
    }

    @PostMapping("/inquire/search")
    public String inquireSearch(@RequestParam String category,
                                Model model) {
        List<Post> inquires = postRepository.categorySearchInquires(category);
        model.addAttribute("inquires", inquires);

        return "managerMain";
    }

    private void userTypeCheck(Model model, HttpServletRequest request) {
        String loginID = getSessionLogin(request);
        User user = userRepository.getUser(loginID);

        userNotFoundCheck(userRepository.getUser(loginID));

        model.addAttribute("user", false);

        if (user instanceof Customer) {
            model.addAttribute("user", true);
        }
    }
}
