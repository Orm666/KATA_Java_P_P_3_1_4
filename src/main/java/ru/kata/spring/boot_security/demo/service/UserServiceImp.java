package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDao.getById(id);
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        userDao.deleteById(id);
    }


}
