import com.clangame.demo.data.db.H2Connector;
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

    private static H2Connector сonnector = new H2Connector();

    @Test
    @DisplayName("Create table in DB")
    @Order(1)
    public void testCreateTableDB() throws SQLException {
        String testQuery = "CREATE TABLE IF NOT EXISTS test(\n" +
                "     test_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                "     name VARCHAR(100) NOT NULL\n" +
                ")";

        PreparedStatement statement = сonnector.getConnection().prepareStatement(testQuery);
        statement.executeUpdate();
        statement.close();
    }

    @Test
    @DisplayName("Write to DB")
    @Order(2)
    public void testWriteDB() throws SQLException {
        String insertQuery = "INSERT INTO test"
                + " (name) VALUES" + " (?)";

        PreparedStatement insertPreparedStatement = сonnector.getConnection()
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

        PreparedStatement statement = сonnector.getConnection().prepareStatement(sql);
        statement.setLong(1, 1);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            assertEquals(1, rs.getLong("test_id"));
            assertEquals("hello from JUnit", rs.getString("name"));
        }

        statement.close();
    }

    @Test
    @DisplayName("Read data from DB")
    @Order(4)
    public void testReadActualDB() throws SQLException {
        String sql = "SELECT * FROM clan WHERE clan_id=?";

        PreparedStatement statement = сonnector.getConnection().prepareStatement(sql);
        statement.setLong(1, 1);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            assertEquals(1, rs.getLong("clan_id"));
            assertEquals("Elfs", rs.getString("name"));
        }

        statement.close();
    }
}
