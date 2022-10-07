package clangame.data.dao;

import clangame.data.entities.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionDAO implements DAO<Transaction> {

    @Override
    public Optional<Transaction> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> getAll() {
        return null;
    }

    @Override
    public void save(Transaction transaction) {

    }

    @Override
    public void update(Transaction transaction, String[] params) {

    }

    @Override
    public void delete(Transaction transaction) {

    }
}
