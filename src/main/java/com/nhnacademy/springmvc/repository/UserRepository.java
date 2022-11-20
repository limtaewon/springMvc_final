package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.CsManager;
import com.nhnacademy.springmvc.domain.Customer;
import com.nhnacademy.springmvc.domain.User;

public interface UserRepository {
    boolean exists(String id);

    Customer registerCustomer(String id, String pwd, String name);
    CsManager registerCsManager(String id, String pwd, String name);

    User getUser(String id);
    boolean matches(String id, String password);
}
