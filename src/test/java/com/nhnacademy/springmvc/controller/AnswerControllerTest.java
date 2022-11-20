package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Answer;
import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Inquire;
import com.nhnacademy.springmvc.exception.NotExistInquireException;
import com.nhnacademy.springmvc.repository.AnswerRepository;
import com.nhnacademy.springmvc.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnswerControllerTest {
    private MockMvc mockMvc;
    private AnswerRepository answerRepository;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        answerRepository = mock(AnswerRepository.class);
        postRepository = mock(PostRepository.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new AnswerController(answerRepository, postRepository))
                .build();
    }

    @Test
    @DisplayName("매니저 문의답변 정상 작성")
    void writeAnswer() throws Exception {
        Answer answer = Answer.create(1, "test");
        Inquire inquire = Inquire.create("test", "test", Category.불만, new ArrayList<>());

        when(answerRepository.writeAnswer(1, "test")).thenReturn(answer);
        when(postRepository.getInquire(anyLong())).thenReturn(inquire);

        mockMvc.perform(post("/cs/admin/answer/{postID}", 1))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("매니저 문의답변 작성 중 문의내용 사라짐")
    void notExistInquireTest() throws Exception {
        Answer answer = Answer.create(1, "test");

        when(answerRepository.writeAnswer(1, "test")).thenReturn(answer);
        when(postRepository.getInquire(anyLong())).thenReturn(null);

        Throwable th = catchThrowable(() ->
                mockMvc.perform(post("/cs/admin/answer/{postID}", 1))
                        .andExpect(status().is3xxRedirection())
                        .andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(NotExistInquireException.class);
    }

    @Test
    @DisplayName("정해진 문의 양식에 부적합한 문의내용")
    void validationFailedTest() throws Exception {
        Answer answer = Answer.create(1, "test");
        Inquire inquire = Inquire.create("test", "test", Category.불만, new ArrayList<>());

        when(answerRepository.writeAnswer(1, "test")).thenReturn(answer);
        when(postRepository.getInquire(anyLong())).thenReturn(inquire);


        mockMvc.perform(post("/cs/admin/answer/{postID}", 1)
                        .param("content", ""))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}