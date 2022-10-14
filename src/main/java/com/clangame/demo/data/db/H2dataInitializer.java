package com.clangame.demo.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2dataInitializer {

    private static Connection connection = null;

    //заполняю базу данными при инициализации
    //только для демо
    static void fillTheDB(Connection connection) {
        H2dataInitializer.connection = connection;

        createSchema();
        fillClanTable();
        fillTaskTable();
        fillUserTable();

        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private static void printDB() throws SQLException {
        String sql = "SELECT * FROM clan";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getLong("clan_id"));
            System.out.println(rs.getString("name"));
        }
    }

    private static void createSchema() {
        String clearQuery = "DROP ALL OBJECTS";
        executeCreateStatement(clearQuery);

        String createQuery = "CREATE TABLE IF NOT EXISTS clan (\n" +
                "    clan_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name VARCHAR(36) NOT NULL,\n" +
                "    gold INTEGER\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE IF NOT EXISTS task (\n" +
                "     task_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "     description VARCHAR(100) NOT NULL,\n" +
                "     gold_given INT\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE IF NOT EXISTS  users (\n" +
                "     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "     name VARCHAR(16) NOT NULL,\n" +
                "     surname VARCHAR(16) NOT NULL,\n" +
                "     clan_id BIGINT\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE IF NOT EXISTS  transaction(\n" +
                "     transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "     clan_id BIGINT NOT NULL,\n" +
                "     delta INT NOT NULL,\n" +
                "     source VARCHAR(8),\n " +
                "     time_stamp TIMESTAMP, \n" +
                "     is_successful BOOLEAN\n" +
                ")";
        executeCreateStatement(createQuery);


        createQuery = "CREATE TABLE IF NOT EXISTS  user_transaction(\n" +
                "    transaction_id BIGINT,\n" +
                "    user_id BIGINT,\n" +
                "    PRIMARY KEY(transaction_id, user_id)\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE IF NOT EXISTS task_transaction(\n" +
                "     transaction_id BIGINT,\n" +
                "     task_id BIGINT,\n" +
                "     PRIMARY KEY(transaction_id, task_id)\n" +
                ")";
        executeCreateStatement(createQuery);

        String alterQuery = "ALTER TABLE users ADD FOREIGN KEY (clan_id) REFERENCES clan(clan_id);\n" +
                "ALTER TABLE transaction ADD FOREIGN KEY (clan_id) REFERENCES clan(clan_id);\n" +
                "ALTER TABLE user_transaction ADD FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id);\n" +
                "ALTER TABLE user_transaction ADD FOREIGN KEY (user_id) REFERENCES users(user_id);\n" +
                "ALTER TABLE task_transaction ADD FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id);\n" +
                "ALTER TABLE task_transaction ADD FOREIGN KEY (task_id) REFERENCES task(task_id);";
        executeCreateStatement(alterQuery);
    }

    private static void executeCreateStatement(String createQuery) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(createQuery);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void fillClanTable() {
        String insertQuery = "INSERT INTO clan " + "(name, gold) VALUES " + "(?,?)";

        insertIntoClanTable(insertQuery, "Elfs", 0);
        insertIntoClanTable(insertQuery, "Humans", 0);
        insertIntoClanTable(insertQuery, "Undead", 0);
        insertIntoClanTable(insertQuery, "Halflings", 0);
    }

    private static void insertIntoClanTable(String query, String name, int gold) {
        try {
            PreparedStatement insertPreparedStatement = connection.prepareStatement(query);
            insertPreparedStatement.setString(1, name);
            insertPreparedStatement.setInt(2, gold);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void fillTaskTable() {
        String insertQuery = "INSERT INTO task"
                + " (description, gold_given) VALUES " + "(?,?)";

        insertIntoClanTable(insertQuery, "You dont have to do anything, just take the money", 100);
    }

    private static void insertIntoTaskTable(String query, String description, int goldGiven) {
        try {
            PreparedStatement insertPreparedStatement = connection.prepareStatement(query);
            insertPreparedStatement.setString(1, description);
            insertPreparedStatement.setInt(2, goldGiven);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void fillUserTable() {
        String insertQuery = "INSERT INTO users "
                + "(name, surname, clan_id) VALUES " + "(?,?,?)";

        insertIntoUserTable(insertQuery, "John", "Doe", 1L);
        insertIntoUserTable(insertQuery, "Jane", "Doe", 1L);
        insertIntoUserTable(insertQuery, "Ivan", "Pirozhkov", 1L);
        insertIntoUserTable(insertQuery, "Anton", "Sergeev", 2L);
        insertIntoUserTable(insertQuery, "Bob", "Welch", 4L);
        insertIntoUserTable(insertQuery, "Lindsey", "Buckingham", 4L);

    }

    private static void insertIntoUserTable(String query, String name, String surname, Long clanId) {
        try {
            PreparedStatement insertPreparedStatement = connection.prepareStatement(query);
            insertPreparedStatement.setString(1, name);
            insertPreparedStatement.setString(2, surname);
            insertPreparedStatement.setLong(3, clanId);
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
