package com.nhnacademy.springmvc.config;

import com.nhnacademy.springmvc.Base;
import com.nhnacademy.springmvc.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.http.HttpServletRequest;

@ComponentScan(basePackageClasses = Base.class)
public class RootConfig {

    @Bean
    public UserRepository customerRepository() {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.registerCustomer("customer", "12345", "고객1");
        userRepository.registerCustomer("customer2", "12345", "고객2");

        userRepository.registerCsManager("csManager", "12345", "매니저1");
        userRepository.registerCsManager("csManager2", "12345", "매니저2");

        return userRepository;
    }

    @Bean
    public PostRepository postRepository(HttpServletRequest httpServletRequest) {
        return new InquireRepository(httpServletRequest);
    }

    @Bean
    public AnswerRepository answerRepository(HttpServletRequest httpServletRequest){
        return new AnswerRepository(httpServletRequest);
    }
}
