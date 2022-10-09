package com.clangame.demo.data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//использую in-memory H2 DB
public class H2Connector {

    private static Connection connection = null;

    private static final String jdbcURL = "jdbc:h2:~/test";
    private static final String jdbcUser = "sa";
    private static final String jdbcPass = "";

    public static Connection getConnection() {
        System.out.println(connection);
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
                H2dataInitializer.fillTheDB(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


}
