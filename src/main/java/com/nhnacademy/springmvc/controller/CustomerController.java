package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.domain.RequestInquire;
import com.nhnacademy.springmvc.method.ImageUpload;
import com.nhnacademy.springmvc.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Controller
public class CustomerController {
    private PostRepository postRepository;

    public CustomerController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/cs")
    public String customerMainView(Model model) {
        List<Post> inquires = postRepository.getUserInquires();
        model.addAttribute("inquires", inquires);

        return "customerMain";
    }

    @GetMapping("/cs/inquire")
    public String inquireView(Model model) {
        List<Category> categories = Stream.of(Category.values()).collect(Collectors.toList());

        model.addAttribute("categories", categories);

        return "inquireForm";
    }

    @PostMapping("/cs/inquire")
    public String writeInquire(@Valid @ModelAttribute RequestInquire requestInquire,
                               MultipartFile[] multipartFiles) {
        List<String> fileNames = ImageUpload.save(multipartFiles);

        postRepository.writeInquires(
                requestInquire.getTitle(),
                requestInquire.getContent(),
                requestInquire.getCategory(),
                fileNames
        );

        return "redirect:/cs";
    }
}
