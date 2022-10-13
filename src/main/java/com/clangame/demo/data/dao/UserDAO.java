package com.clangame.demo.data.dao;

import com.clangame.demo.data.db.H2Connector;
import com.clangame.demo.data.entities.User;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User, Long> {

    @Inject
    H2Connector connector;

    @Override
    public Optional<User> get(Long id) {
        String sql = "SELECT * FROM users WHERE user_id=?";
        User user = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getLong("user_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("user_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public void save(User user) {
        String insertQuery = "INSERT INTO users " + "(name, surname) VALUES (?,?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery)) {
            insertPreparedStatement.setString(1, user.getName());
            insertPreparedStatement.setString(2, user.getSurname());
            insertPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String updateQuery = "UPDATE users SET name=?, surname=? "
                + "WHERE user_id=?";
        try (Connection connection = connector.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, user.getName());
            updateStatement.setString(2, user.getSurname());
            updateStatement.setLong(3, user.getId());
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE user_id=?";
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
