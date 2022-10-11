package com.clangame.demo.data.dao;

import com.clangame.demo.data.db.H2Connector;
import com.clangame.demo.data.entities.TaskTransaction;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.data.entities.UserAddGoldTransaction;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserTransactionDAO implements DAO<UserAddGoldTransaction, Long> {

    @Inject
    private H2Connector connector;

    @Inject
    private TransactionDAO transactionDAO;

    @Override
    public Optional<UserAddGoldTransaction> get(Long id) {
        String sql = "SELECT t.transaction_id, t.clan_id, t.delta, t.source, t.is_successful, ut.user_id \n" +
                "FROM user_transaction ut LEFT JOIN transaction t ON ut.transaction_id = t.transaction_id \n" +
                "WHERE transaction_id=?";
        UserAddGoldTransaction userTransaction = null;
        try (Connection connection = connector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                userTransaction = extractTransaction(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(userTransaction);
    }

    @Override
    public List<UserAddGoldTransaction> getAll() {
        String sql = "SELECT t.transaction_id, t.clan_id, t.delta, t.source, t.is_successful, ut.user_id \n" +
                "FROM user_transaction ut LEFT JOIN transaction t ON ut.transaction_id = t.transaction_id";
        List<UserAddGoldTransaction> transactions = new ArrayList<>();
        try (Connection connection = connector.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                UserAddGoldTransaction userTransaction = extractTransaction(rs);
                transactions.add(userTransaction);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return transactions;
    }

    private UserAddGoldTransaction extractTransaction(ResultSet rs) throws SQLException {
        UserAddGoldTransaction userTransaction = new UserAddGoldTransaction();
        userTransaction.setUserId(rs.getLong("user_id"));
        Transaction transaction = transactionDAO.get(rs.getLong("transaction_id")).get();
        userTransaction.setTransaction(transaction);
        return userTransaction;
    }

    @Override
    public void save(UserAddGoldTransaction userTransaction) {
        String insertQuery = "INSERT INTO user_transaction "
                + "(transaction_id, user_id) VALUES (?,?)";
        try (Connection connection = connector.getConnection()) {
            transactionDAO.save(userTransaction.getTransaction());
            long transactionId = transactionDAO.getAll().size();
            PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
            insertPreparedStatement.setLong(1, transactionId);
            insertPreparedStatement.setLong(2, userTransaction.getUserId());
            insertPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(UserAddGoldTransaction userTransaction) {
        //Нечего обновлять, так как все поля таблицы - внешние ключи
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM user_transaction " +
                "WHERE transaction_id=?";
        try (Connection connection = connector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
