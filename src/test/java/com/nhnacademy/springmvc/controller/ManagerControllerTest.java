package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ManagerControllerTest {

    private PostRepository postRepository;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.postRepository = mock(PostRepository.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ManagerController(postRepository))
                .build();
    }

    @Test
    @DisplayName("매니저의 메인화면 출력 테스트")
    void managerMain() throws Exception {
        when(postRepository.getAdminInquires()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/cs/admin"))
                .andExpect(model().attributeExists("inquires"))
                .andExpect(status().isOk())
                .andExpect(view().name("managerMain"));
    }
}