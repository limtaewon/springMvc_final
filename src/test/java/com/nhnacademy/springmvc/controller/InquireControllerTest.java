package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.*;
import com.nhnacademy.springmvc.exception.NotExistInquireException;
import com.nhnacademy.springmvc.exception.NotFoundFileException;
import com.nhnacademy.springmvc.repository.AnswerRepository;
import com.nhnacademy.springmvc.repository.PostRepository;
import com.nhnacademy.springmvc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class InquireControllerTest {
    private MockMvc mockMvc;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        this.userRepository = mock(UserRepository.class);
        this.postRepository = mock(PostRepository.class);
        this.answerRepository = mock(AnswerRepository.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new InquireController(postRepository, userRepository, answerRepository))
                .build();
    }

    @Test
    @DisplayName("문의 상세내용 고객아이디로 접속 테스트")
    void inquireDetail_Customer() throws Exception {
        Answer answer = Answer.create(1, "test");
        Inquire inquire = Inquire.create("test", "test", Category.불만, new ArrayList<>());
        User user = new Customer("customer", "12345", "손님");

        when(postRepository.getInquire(1)).thenReturn(inquire);
        when(answerRepository.getAnswer(1)).thenReturn(answer);

        when(userRepository.getUser(any())).thenReturn(user);

        mockMvc.perform(get("/inquire/{inquireID}", 1))
                .andExpect(model().attribute("user", true))
                .andExpect(model().attributeExists("inquire"))
                .andExpect(model().attributeExists("answer"))
                .andExpect(status().isOk())
                .andExpect(view().name("inquireDetail"))
                .andDo(print());
    }

    @Test
    @DisplayName("문의 상세내용 매니저아이디로 접속 테스트")
    void inquireDetail_Admin() throws Exception {
        Answer answer = Answer.create(1, "test");
        Inquire inquire = Inquire.create("test", "test", Category.불만, new ArrayList<>());
        User user = new CsManager("csManager", "12345", "csManager");

        when(postRepository.getInquire(1)).thenReturn(inquire);
        when(answerRepository.getAnswer(1)).thenReturn(answer);

        when(userRepository.getUser(any())).thenReturn(user);

        mockMvc.perform(get("/inquire/{inquireID}", 1))
                .andExpect(model().attribute("user", false))
                .andExpect(model().attributeExists("inquire"))
                .andExpect(model().attributeExists("answer"))
                .andExpect(status().isOk())
                .andExpect(view().name("inquireDetail"))
                .andDo(print());
    }

    @Test
    @DisplayName("문의 상세내용 접속했지만 문의가 사라진 에러 테스트")
    void noExistInquire() throws Exception {
        when(postRepository.getInquire(1)).thenReturn(null);

        Throwable th = catchThrowable(() -> mockMvc.perform(get("/inquire/{inquireID}", 1))
                .andExpect(status().isBadRequest())
                .andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(NotExistInquireException.class);
    }

    @Test
    @DisplayName("문의 카테고리로 검색")
    void inquireSearch() throws Exception {
        List<Post> inquires = new ArrayList<>();

        when(postRepository.categorySearchInquires(any())).thenReturn(inquires);

        mockMvc.perform(post("/inquire/search")
                .param("category","불만"))
                .andExpect(view().name("managerMain"))
                .andExpect(model().attributeExists("inquires"))
                .andExpect(status().isOk());
    }
}