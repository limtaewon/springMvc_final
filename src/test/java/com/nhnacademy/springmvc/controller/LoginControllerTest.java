package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.CsManager;
import com.nhnacademy.springmvc.domain.Customer;
import com.nhnacademy.springmvc.domain.User;
import com.nhnacademy.springmvc.exception.NotExistUserException;
import com.nhnacademy.springmvc.exception.NotFoundFileException;
import com.nhnacademy.springmvc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class LoginControllerTest {
    private UserRepository userRepository;
    private MockMvc mockMvc;
    private MockHttpServletRequest request;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        this.request = new MockHttpServletRequest();
        this.session = new MockHttpSession();

        this.userRepository = mock(UserRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(userRepository))
                .build();
    }

    @Test
    void LoginCustomerSessionExist() throws Exception {
        User user = new Customer("customer", "12345", "손님");

        when(userRepository.getUser(anyString())).thenReturn(user);

        session.setAttribute("login", "customer");

        mockMvc.perform(get("/cs/login")
                        .session(session))
                .andExpect(redirectedUrl("/cs"));
    }

    @Test
    void LoginCsManagerSessionExist() throws Exception {
        User user = new CsManager("csManager", "12345", "매니저");

        when(userRepository.getUser(anyString())).thenReturn(user);

        session.setAttribute("login", "csManager");

        mockMvc.perform(get("/cs/login")
                        .session(session))
                .andExpect(redirectedUrl("/cs/admin"));
    }

    @Test
    void LoginSessionNotExist() throws Exception {
        mockMvc.perform(get("/cs/login")
                        .session(session))
                .andExpect(view().name("loginForm"));
    }


    @Test
    @DisplayName("유저세션은 존재하지만 계정이 없는경우 -> 삭제되거나 불법적인 접근")
    void LoginSessionExistButNotUser() throws Exception {
        when(userRepository.getUser(anyString())).thenReturn(null);

        session.setAttribute("login", "customer");

        Throwable th = catchThrowable(() -> mockMvc.perform(get("/cs/login")
                        .session(session))
                .andExpect(redirectedUrl("/cs")));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(NotExistUserException.class);
    }

    @Test
    void postLogin() throws Exception {
        User user = new Customer("customer1", "12345", "손님");

        when(userRepository.matches(anyString(), anyString()))
                .thenReturn(true);

        when(userRepository.getUser(anyString())).thenReturn(user);

        mockMvc.perform(post("/cs/login")
                .param("id","customer1")
                .param("password","12345")
                .session(session))
                .andExpect(redirectedUrl("/cs"));
    }

    @Test
    void notMatchLoginID() throws Exception {
        when(userRepository.matches(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/cs/login")
                        .param("id","customer1")
                        .param("password","12345")
                        .session(session))
                .andExpect(view().name("loginForm"));
    }


}