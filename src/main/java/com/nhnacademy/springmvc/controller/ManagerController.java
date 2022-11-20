package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ManagerController {

    private final PostRepository postRepository;

    public ManagerController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/cs/admin")
    public String managerMain(Model model) {
        List<Post> inquires = postRepository.getAdminInquires();
        model.addAttribute("inquires", inquires);

        return "managerMain";
    }
}
