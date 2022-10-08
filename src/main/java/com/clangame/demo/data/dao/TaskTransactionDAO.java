package com.clangame.demo.data.dao;

import com.clangame.demo.data.entities.TaskTransaction;

import java.util.List;
import java.util.Optional;

public class TaskTransactionDAO implements DAO<TaskTransaction> {
    @Override
    public Optional<TaskTransaction> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<TaskTransaction> getAll() {
        return null;
    }

    @Override
    public void save(TaskTransaction taskTransaction) {

    }

    @Override
    public void update(TaskTransaction taskTransaction, String[] params) {

    }

    @Override
    public void delete(TaskTransaction taskTransaction) {

    }
}
