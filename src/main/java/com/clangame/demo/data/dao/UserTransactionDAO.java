package com.clangame.demo.data.dao;

import com.clangame.demo.data.entities.UserAddGoldTransaction;

import java.util.List;
import java.util.Optional;

public class UserTransactionDAO implements DAO<UserAddGoldTransaction> {

    @Override
    public Optional<UserAddGoldTransaction> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<UserAddGoldTransaction> getAll() {
        return null;
    }

    @Override
    public void save(UserAddGoldTransaction userAddGoldTransaction) {

    }

    @Override
    public void update(UserAddGoldTransaction userAddGoldTransaction, String[] params) {

    }

    @Override
    public void delete(UserAddGoldTransaction userAddGoldTransaction) {

    }
}
