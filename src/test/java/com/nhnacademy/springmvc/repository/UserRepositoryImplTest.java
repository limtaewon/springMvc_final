package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.CsManager;
import com.nhnacademy.springmvc.domain.Customer;
import com.nhnacademy.springmvc.domain.User;
import com.nhnacademy.springmvc.exception.AlreadyExistsException;
import com.nhnacademy.springmvc.exception.NotExistCategoryNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.CLASS;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Test
    void registerCustomer_success() {
        userRepository.registerCustomer("customer", "12345", "name");

        User customer = userRepository.getUser("customer");
        assertThat(customer.getId()).isEqualTo("customer");
        assertThat(customer.getPwd()).isEqualTo("12345");
        assertThat(customer.getName()).isEqualTo("name");
        assertThat(customer.getClass().getName()).isEqualTo("com.nhnacademy.springmvc.domain.Customer");
    }

    @Test
    void registerCsManager() {
        userRepository.registerCsManager("csManager", "12345", "name");

        User csManager = userRepository.getUser("csManager");
        assertThat(csManager.getId()).isEqualTo("csManager");
        assertThat(csManager.getPwd()).isEqualTo("12345");
        assertThat(csManager.getName()).isEqualTo("name");
        assertThat(csManager.getClass().getName()).isEqualTo("com.nhnacademy.springmvc.domain.CsManager");
    }

    @Test
    void register_already_exist() {
        userRepository.registerCsManager("csManager", "12345", "name");

        Assertions.assertThatThrownBy(() -> userRepository.registerCsManager("csManager", "12345", "name"))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("이미 존재하는 id입니다 : csManager");
    }

    @Test
    void exists_false() {
        boolean exists = userRepository.exists("customer");

        assertThat(exists).isFalse();
    }

    @Test
    void exists_true() {
        userRepository.registerCustomer("customer","12345","name");
        boolean exists = userRepository.exists("customer");

        assertThat(exists).isTrue();
    }

    @Test
    void getUser_null() {
        User customer = userRepository.getUser("customer");

        assertThat(customer).isNull();
    }

    @Test
    void getUser() {
        userRepository.registerCustomer("customer", "12345", "name");
        User customer = userRepository.getUser("customer");

        assertThat(customer.getId()).isEqualTo("customer");
        assertThat(customer.getPwd()).isEqualTo("12345");
        assertThat(customer.getName()).isEqualTo("name");
    }

    @Test
    void matches_true() {
        userRepository.registerCustomer("customer", "12345", "name");
        boolean customer = userRepository.matches("customer", "12345");

        assertThat(customer).isTrue();
    }

    @Test
    void matches_false() {
        userRepository.registerCustomer("csManager", "12345", "name");
        boolean customer = userRepository.matches("customer", "12345");

        assertThat(customer).isFalse();
    }
}