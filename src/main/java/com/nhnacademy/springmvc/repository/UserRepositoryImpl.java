package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.CsManager;
import com.nhnacademy.springmvc.domain.Customer;
import com.nhnacademy.springmvc.domain.User;
import com.nhnacademy.springmvc.exception.AlreadyExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public boolean exists(String id) {
        return users.containsKey(id);
    }

    @Override
    public Customer registerCustomer(String id, String pwd, String name) {
        existErrorCheck(id);
        return (Customer) users.put(id, new Customer(id, pwd, name));
    }

    @Override
    public CsManager registerCsManager(String id, String pwd, String name) {
        existErrorCheck(id);
        return (CsManager) users.put(id, new CsManager(id, pwd, name));
    }

    @Override
    public User getUser(String id) { return exists(id) ? users.get(id) : null; }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getUser(id))
                .map(user -> user.getPwd().equals(password))
                .orElse(false);
    }

    private void existErrorCheck(String id) {
        if (exists(id)) { throw new AlreadyExistsException(id); }
    }
}
