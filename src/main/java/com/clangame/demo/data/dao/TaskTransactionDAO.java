package com.clangame.demo.data.dao;

import com.clangame.demo.data.db.H2Connector;
import com.clangame.demo.data.entities.Clan;
import com.clangame.demo.data.entities.TaskTransaction;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.exception.TransactionIsNotCommittedException;
import lombok.SneakyThrows;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskTransactionDAO implements DAO<TaskTransaction, Long> {

    @Inject
    private H2Connector connector;

    @Inject
    private TransactionDAO transactionDAO;

    @Inject
    private ClanDAO clanDAO;

    @Override
    public Optional<TaskTransaction> get(Long id) {
        String sql = "SELECT t.transaction_id, t.clan_id, t.delta, t.source, t.is_successful, tt.task_id \n" +
                "FROM task_transaction tt LEFT JOIN transaction t ON tt.transaction_id = t.transaction_id \n" +
                "WHERE transaction_id=?";
        TaskTransaction taskTransaction = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                taskTransaction = extractTransaction(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(taskTransaction);
    }

    @Override
    public List<TaskTransaction> getAll() {
        String sql = "SELECT t.transaction_id, t.clan_id, t.delta, t.source, t.is_successful, tt.task_id \n" +
                "FROM task_transaction tt LEFT JOIN transaction t ON tt.transaction_id = t.transaction_id";
        List<TaskTransaction> transactions = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                TaskTransaction taskTransaction = extractTransaction(rs);
                transactions.add(taskTransaction);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return transactions;
    }

    private TaskTransaction extractTransaction(ResultSet rs) throws SQLException {
        TaskTransaction taskTransaction = new TaskTransaction();
        taskTransaction.setTaskId(rs.getLong("task_id"));
        Transaction transaction = transactionDAO.get(rs.getLong("transaction_id")).get();
        taskTransaction.setTransaction(transaction);
        return taskTransaction;
    }

    @Override
    public void save(TaskTransaction taskTransaction) {
        String insertQuery = "INSERT INTO task_transaction "
                + "(transaction_id, task_id) VALUES (?,?)";
        try (Connection connection = connector.getConnection()) {
            transactionDAO.save(taskTransaction.getTransaction());
            long transactionId = transactionDAO.getAll().size();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
            insertPreparedStatement.setLong(1, transactionId);
            insertPreparedStatement.setLong(2, taskTransaction.getTaskId());
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @SneakyThrows
    public synchronized void saveAndEditRelatedClan(TaskTransaction taskTransaction) throws TransactionIsNotCommittedException {
        String insertQuery = "INSERT INTO task_transaction "
                + "(transaction_id, task_id) VALUES (?,?)";
        Connection connection = null;
        boolean committed = false;
        try {
            connection = connector.getConnection();
            connection.setAutoCommit(false);
            transactionDAO.save(taskTransaction.getTransaction());
            long transactionId = transactionDAO.getAll().size();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
            insertPreparedStatement.setLong(1, transactionId);
            insertPreparedStatement.setLong(2, taskTransaction.getTaskId());
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();

            Clan clan = clanDAO.get(taskTransaction.getClanId(), connection).get();
            int oldBalance = clan.getGold();
            clan.setGold(oldBalance + taskTransaction.getTransaction().getDelta());
            clanDAO.update(clan, connection);
            connection.commit();
            committed = true;
        } catch (SQLException ex) {
            System.err.print("Transaction is being rolled back");
            connection.rollback();
        } finally {
            connection.close();
        }
        if (!committed)
            throw new TransactionIsNotCommittedException();
    }

    @Override
    public void update(TaskTransaction taskTransaction) {
        //нельзя ничего обновить в таблице taskTransaction
        // - все колонки таблицы - внешние ключи
        // поэтому просто обновим таблицу transaction
        transactionDAO.update(taskTransaction.getTransaction());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM task_transaction " +
                "WHERE transaction_id=?";
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
