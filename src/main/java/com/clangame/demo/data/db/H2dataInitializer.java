package com.clangame.demo.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    }

    private static void createSchema() {
        PreparedStatement createPreparedStatement = null;
        String createQuery = "CREATE TABLE clan (\n" +
                "    clan_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name VARCHAR(36) NOT NULL,\n" +
                "    gold INTEGER\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE task(\n" +
                "     task_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "     description VARCHAR(100) NOT NULL,\n" +
                "     gold_given INT\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE user(\n" +
                "     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "     name VARCHAR(16) NOT NULL,\n" +
                "     surname VARCHAR(16) NOT NULL,\n" +
                "     clan_id BIGINT\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE transaction(\n" +
                "     transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "     clan_id BIGINT NOT NULL,\n" +
                "     delta INT NOT NULL,\n" +
                "     source INT,\n" +
                "     is_successful BOOLEAN\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE user_transaction(\n" +
                "    transaction_id BIGINT PRIMARY KEY,\n" +
                "    user_id BIGINT PRIMARY KEY\n" +
                ")";
        executeCreateStatement(createQuery);

        createQuery = "CREATE TABLE task_transaction(\n" +
                "     transaction_id BIGINT PRIMARY KEY,\n" +
                "     task_id BIGINT PRIMARY KEY\n" +
                ")";
        executeCreateStatement(createQuery);

        String alterQuery = "ALTER TABLE user ADD FOREIGN KEY (clan_id) REFERENCES clan(clan_id);\n" +
                "ALTER TABLE transaction ADD FOREIGN KEY (clan_id) REFERENCES clan(clan_id);\n" +
                "ALTER TABLE user_transaction ADD FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id);\n" +
                "ALTER TABLE user_transaction ADD FOREIGN KEY (user_id) REFERENCES user(user_id);\n" +
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
        String insertQuery = "INSERT INTO clan" + "(name, gold) VALUES" + "(?,?)";

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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void fillTaskTable() {
        String insertQuery = "INSERT INTO task"
                + " (description, gold_given) VALUES" + "(?,?)";

        insertIntoClanTable(insertQuery, "You dont have to do anything, just take the money", 100);
    }

    private static void insertIntoTaskTable(String query, String description, int goldGiven) {
        try {
            PreparedStatement insertPreparedStatement = connection.prepareStatement(query);
            insertPreparedStatement.setString(1, description);
            insertPreparedStatement.setInt(2, goldGiven);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void fillUserTable() {
        String insertQuery = "INSERT INTO user"
                + "(name, surname, clan_id) VALUES" + "(?,?,?)";

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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
