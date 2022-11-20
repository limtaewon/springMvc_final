package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.exception.NotFoundFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ImageControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ImageController())
                .build();
    }

    @Test
    @DisplayName("이미지 페이지 테스트")
    void imageView() throws Exception {
        mockMvc.perform(get("/image/{fileName}", "test.png"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andDo(print());
    }

    @Test
    @DisplayName("이미지 페이지 접속했지만 없는 이미지 에러")
    void imageNotFound() throws Exception {
        Throwable th = catchThrowable(() -> mockMvc.perform(get("/image/{fileName}", "test.jpg"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(NotFoundFileException.class);
    }
}