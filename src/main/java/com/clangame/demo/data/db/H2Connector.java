package com.clangame.demo.data.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class H2Connector {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    private static final String driver = "org.h2.Driver";
    private static final String jdbcURL = "jdbc:h2:~/test";
    private static final String jdbcUser = "sa";
    private static final String jdbcPass = "";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        config.setJdbcUrl("jdbc:h2:~/test");
        config.setUsername("sa");
        config.setPassword("");
        config.setConnectionTimeout(50000);
        config.setMaximumPoolSize(102);
        config.setLeakDetectionThreshold(1000);
        dataSource = new HikariDataSource(config);

        try {
            H2dataInitializer.fillTheDB(dataSource.getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @SneakyThrows
    public Connection getConnection() throws SQLException {
        Class.forName(driver);
        return dataSource.getConnection();
    }

}
