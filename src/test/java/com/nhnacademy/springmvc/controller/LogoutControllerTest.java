package com.nhnacademy.springmvc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class LogoutControllerTest {
    private MockMvc mockMvc;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LogoutController())
                .build();

        this.session = new MockHttpSession();
    }

    @Test
    @DisplayName("로그아웃 정상작동")
    void logout() throws Exception {
        session.setAttribute("login","customer");

        mockMvc.perform(get("/cs/logout")
                .session(session))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("로그인이 안된 상태로 로그아웃 시도")
    void try_logout_but_notExist_loginSession() throws Exception {
        mockMvc.perform(get("/cs/logout")
                        .session(session))
                .andExpect(view().name("loginForm"));
    }
}