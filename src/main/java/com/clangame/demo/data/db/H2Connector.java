package com.clangame.demo.data.db;

import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class H2Connector {

    private boolean initIsNeeded = true;

    private static final String driver = "org.h2.Driver";
    private static final String jdbcURL = "jdbc:h2:~/test";
    private static final String jdbcUser = "sa";
    private static final String jdbcPass = "";

    private volatile Connection connection;

    @SneakyThrows
    public Connection getConnection() {
        Class.forName(driver);

        if (initIsNeeded) {
            H2dataInitializer.fillTheDB(DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass));
            initIsNeeded = false;
        }

        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

}
