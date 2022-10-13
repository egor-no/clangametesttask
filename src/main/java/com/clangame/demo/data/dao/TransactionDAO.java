package com.clangame.demo.data.dao;

import com.clangame.demo.data.db.H2Connector;
import com.clangame.demo.data.entities.TaskTransaction;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.data.tools.GoldSource;

import javax.inject.Inject;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAO implements DAO<Transaction, Long> {

    @Inject
    H2Connector connector;

    @Override
    public Optional<Transaction> get(Long id) {
        String sql = "SELECT * FROM transaction WHERE transaction_id=?";
        Transaction transaction = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                transaction = extractTransaction(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        String sql = "SELECT * FROM transaction";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                transactions.add(extractTransaction(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> getAllByClanId(long clanId) {
        String sql = "SELECT * FROM transaction WHERE clan_id=?";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, clanId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                transactions.add(extractTransaction(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return transactions;
    }

    private Transaction extractTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong("transaction_id"));
        transaction.setClanId(rs.getLong("clan_id"));
        transaction.setDelta(rs.getInt("delta"));
        transaction.setSource(GoldSource.valueOf(rs.getString("source")));
        transaction.setSuccessful(rs.getBoolean("is_successful"));
        return transaction;
    }

    @Override
    public synchronized void save(Transaction transaction) {
        String insertQuery = "INSERT INTO transaction " +
                "(clan_id, delta, source, is_successful) VALUES " + "(?,?,?,?)";
        try (Connection connection = connector.getConnection();
             PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery)) {
            insertPreparedStatement.setLong(1, transaction.getClanId());
            insertPreparedStatement.setInt(2, transaction.getDelta());
            insertPreparedStatement.setString(3, transaction.getSource().name());
            insertPreparedStatement.setBoolean(4, transaction.isSuccessful());
            insertPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Transaction transaction) {
        String updateQuery = "UPDATE transaction " +
                "SET clan_id=?, delta=?, source=?, is_successful=? " +
                "WHERE transaction_id=?";
        try (Connection connection = connector.getConnection();
             PreparedStatement insertPreparedStatement = connection.prepareStatement(updateQuery)) {
            insertPreparedStatement.setLong(1, transaction.getClanId());
            insertPreparedStatement.setInt(2, transaction.getDelta());
            insertPreparedStatement.setString(3, transaction.getSource().name());
            insertPreparedStatement.setBoolean(4, transaction.isSuccessful());
            insertPreparedStatement.setLong(5, transaction.getId());
            insertPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM transaction WHERE transaction_id=?";
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
