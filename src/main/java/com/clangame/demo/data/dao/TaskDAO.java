package com.clangame.demo.data.dao;

import com.clangame.demo.data.db.H2Connector;
import com.clangame.demo.data.entities.Task;
import com.clangame.demo.data.entities.User;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDAO implements DAO<Task, Long> {

    @Inject
    H2Connector connector;

    @Override
    public Optional<Task> get(Long id) {
        String sql = "SELECT * FROM task WHERE task_id=?";
        Task task = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                task = new Task();
                task.setId(rs.getLong("task_id"));
                task.setDescription(rs.getString("description"));
                task.setGoldGiven(rs.getInt("gold_given"));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.ofNullable(task);
    }

    @Override
    public List<Task> getAll() {
        String sql = "SELECT * FROM task";
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getLong("task_id"));
                task.setDescription(rs.getString("description"));
                task.setGoldGiven(rs.getInt("gold_given"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    @Override
    public void save(Task task) {
        String insertQuery = "INSERT INTO task (description, gold_given) VALUES (?,?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);) {
            insertPreparedStatement.setString(1, task.getDescription());
            insertPreparedStatement.setInt(2, task.getGoldGiven());
            insertPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Task task) {
        String updateQuery = "UPDATE task SET description=?, gold_given=?"
                + " WHERE task_id=?";
        try (Connection connection = connector.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery);) {
            updateStatement.setString(1, task.getDescription());
            updateStatement.setInt(2, task.getGoldGiven());
            updateStatement.setLong(3, task.getId());
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM task WHERE task_id=?";
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
