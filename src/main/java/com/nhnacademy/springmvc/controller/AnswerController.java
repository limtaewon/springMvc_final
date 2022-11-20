package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Inquire;
import com.nhnacademy.springmvc.domain.RequestAnswer;
import com.nhnacademy.springmvc.exception.NotExistInquireException;
import com.nhnacademy.springmvc.repository.AnswerRepository;
import com.nhnacademy.springmvc.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class AnswerController {
    private final AnswerRepository answerRepository;
    private final PostRepository postRepository;

    public AnswerController(AnswerRepository answerRepository, PostRepository postRepository) {
        this.answerRepository = answerRepository;
        this.postRepository = postRepository;
    }

    @PostMapping("/cs/admin/answer/{postID}")
    public String writeAnswer(@Valid @ModelAttribute RequestAnswer answer,
                              @PathVariable long postID,
                              Model model) {
        Inquire inquire = postRepository.getInquire(postID);

        answerRepository.writeAnswer(postID, answer.getContent());


        if (Objects.isNull(inquire)) {
            throw new NotExistInquireException();
        }

        inquire.setAnswer(true);
        model.addAttribute("inquire", inquire);
        return "redirect:/cs/admin";
    }
}
