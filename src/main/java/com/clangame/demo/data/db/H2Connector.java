package com.clangame.demo.data.db;

import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class H2Connector {

    private Connection connection = null;

    private final String jdbcURL = "jdbc:h2:~/test";
    private final String jdbcUser = "sa";
    private final String jdbcPass = "";

    @SneakyThrows
    public Connection getConnection() {
        Class.forName("org.h2.Driver");
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
