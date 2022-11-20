package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Answer;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.nhnacademy.springmvc.method.SessionLogin.getSessionLogin;

public class AnswerRepository {
    private static long id = 0;
    private final Map<Long, Answer> answers = new HashMap<>();
    private final HttpServletRequest httpServletRequest;

    public AnswerRepository(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public Answer writeAnswer(long postID, String content) {
        String loginID = getSessionLogin(httpServletRequest);
        Answer answer = Answer.create(postID, content);

        answer.setId(++id);
        answer.setManagerID(loginID);

        answers.put(id, answer);

        return answer;
    }

    public Answer getAnswer(long postID) {
        Optional<Answer> opAnswer = answers.values().stream().filter(answer -> answer.getPostID() == postID).findFirst();

        return opAnswer.orElse(null);
    }
}
