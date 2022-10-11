package com.clangame.demo.services;

import com.clangame.demo.data.dao.UserDAO;
import com.clangame.demo.data.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    private UserDAO userDAO;

    public User get(long userId) {
        return userDAO.get(userId).orElse(null);
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public long save(User user) {
        userDAO.save(user);
        long id = getAll().size();
        return id;
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void delete(long userId) {
        userDAO.delete(userId);
    }

}
