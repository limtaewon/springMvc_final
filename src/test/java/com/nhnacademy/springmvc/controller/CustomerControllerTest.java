package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Inquire;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class CustomerControllerTest {
    private MockMvc mockMvc;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(postRepository))
                .build();
    }

    @Test
    @DisplayName("고객의 메인화면 테스트")
    void customerMain() throws Exception {
        Inquire inquire = Inquire.create("test", "test", Category.불만, new ArrayList<>());
        List<Post> inquires = new ArrayList<>();
        inquires.add(inquire);

        when(postRepository.getUserInquires()).thenReturn(inquires);

        mockMvc.perform(get("/cs"))
                .andExpect(view().name("customerMain"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("문의작성 페이지 테스트")
    void inquireView() throws Exception {
        mockMvc.perform(get("/cs/inquire"))
                .andExpect(view().name("inquireForm"))
                .andExpect(status().isOk());
    }

}