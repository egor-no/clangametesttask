package com.clangame.demo.services;

import com.clangame.demo.data.dao.UserDAO;
import com.clangame.demo.data.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserService {

    @Inject
    private UserDAO userDAO;

    public User get(long userId) {
        return userDAO.get(userId).orElse(null);
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void delete(User user) {
        userDAO.delete(user);
    }

}
