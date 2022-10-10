package com.clangame.demo.data.db;

import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class H2Connector {

    private boolean initIsNeeded = true;

    private final String jdbcURL = "jdbc:h2:~/test";
    private final String jdbcUser = "sa";
    private final String jdbcPass = "";

    @SneakyThrows
    public Connection getConnection() {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);

        if (initIsNeeded) {
            H2dataInitializer.fillTheDB(connection);
            initIsNeeded = false;
        }

        return connection;
    }


}
