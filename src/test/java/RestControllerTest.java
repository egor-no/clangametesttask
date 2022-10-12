import com.clangame.demo.data.db.H2Connector;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RestControllerTest {

    private static H2Connector connector = new H2Connector();

    @Test
    @Timeout(30)
    @DisplayName("Clan doesn't exist")
    public void testForClanThatDoesNotExist() throws IOException, SQLException {

        connector.getConnection();

        //такой клан не может существовать
        HttpUriRequest request = new HttpGet("http://localhost:8080/ClanGame-1.0-SNAPSHOT/clan/0");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertThat(httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
    }

}
