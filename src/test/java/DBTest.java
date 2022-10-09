import com.clangame.demo.data.db.H2Connector;
import com.clangame.demo.data.db.H2dataInitializer;
import com.clangame.demo.data.entities.Clan;
import com.clangame.demo.services.ClanService;
import com.clangame.demo.services.ClanServiceImpl;
import com.clangame.demo.services.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Тесты, чтобы следить, что связь с базой данных поддерживается
//Проверяю чтение Клана и его сохранение

public class DBTest {

    @Test
    @DisplayName("Create table in DB")
    @Order(1)
    public void testCreateTableDB() throws SQLException {
        String testQuery = "CREATE TABLE IF NOT EXISTS test(\n" +
                "     test_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "     name VARCHAR(100) NOT NULL\n" +
                ")";

        PreparedStatement statement = H2Connector.getConnection().prepareStatement(testQuery);
        statement.executeUpdate();
        statement.close();
    }

    @Test
    @DisplayName("Write to DB")
    @Order(2)
    public void testWriteDB() throws SQLException {
        String insertQuery = "INSERT INTO test"
                + " (name) VALUES" + " (?)";

        PreparedStatement insertPreparedStatement = H2Connector.getConnection()
                .prepareStatement(insertQuery);
        insertPreparedStatement.setString(1, "hello from JUnit");

        insertPreparedStatement.executeUpdate();
        insertPreparedStatement.close();
    }

    @Test
    @DisplayName("Read from DB")
    @Order(3)
    public void testReadDB() throws SQLException {
        String sql = "SELECT * FROM test WHERE test_id=?";

        PreparedStatement ps = H2Connector.getConnection().prepareStatement(sql);
        ps.setLong(1, 1);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            assertEquals(1, rs.getLong("test_id"));
            assertEquals("hello from JUnit", rs.getString("name"));
        }
    }
}
