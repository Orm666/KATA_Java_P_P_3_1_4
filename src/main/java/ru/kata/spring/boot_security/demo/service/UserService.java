package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User findByUsername(String username);

    void save(User user);

    User getUserById(Long id);

    void update(User user);

    void remove(Long id);
}
