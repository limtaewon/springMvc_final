package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Answer;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnswerRepositoryTest {
    private MockHttpServletRequest request;
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        this.request = new MockHttpServletRequest();
        answerRepository = new AnswerRepository(request);
    }

    @Test
    @Order(1)
    void writeAnswer() {
        Answer saveAnswer = answerRepository.writeAnswer(1,"test");

        assertThat(saveAnswer.getPostID()).isEqualTo(1);
        assertThat(saveAnswer.getContent()).isEqualTo("test");
        assertThat(saveAnswer.getId()).isEqualTo(1);
        assertThat(saveAnswer.getManagerID()).isNull();
    }

    @Test
    @Order(2)
    void getAnswer() {
        answerRepository.writeAnswer(1,"test");

        Answer saveAnswer = answerRepository.getAnswer(1);

        assertThat(saveAnswer.getPostID()).isEqualTo(1);
        assertThat(saveAnswer.getContent()).isEqualTo("test");
        assertThat(saveAnswer.getId()).isEqualTo(2);
        assertThat(saveAnswer.getManagerID()).isNull();

    }
    @Test
    @Order(3)
    void getAnswerFiled() {
        Answer saveAnswer = answerRepository.getAnswer(1);

        assertThat(saveAnswer).isNull();
    }
}