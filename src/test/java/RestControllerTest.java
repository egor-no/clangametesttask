import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.MatcherAssert;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RestControllerTest {

    @Test
    @Order(1)
    @DisplayName("Clan doesn't exist")
    public void testForClanThatDoesNotExist() throws IOException {

        //такой клан не может существовать
        HttpUriRequest request = new HttpGet("http://localhost:8080/ClanGame-1.0-SNAPSHOT/clan/0");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        MatcherAssert.assertThat(httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    @Order(2)
    @DisplayName("Task 1 exists")
    public void testIfTaskExistsAndCodeIsOk() throws IOException, SQLException {

        HttpUriRequest request = new HttpGet("http://localhost:8080/ClanGame-1.0-SNAPSHOT/task/1");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        MatcherAssert.assertThat(httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    @Order(3)
    @DisplayName("Test Media type")
    public void testTheMediaType() throws IOException {

        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/ClanGame-1.0-SNAPSHOT/task/1" );

        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );
    }

    @Test
    @Order(4)
    @DisplayName("Post a new user")
    public void testNewUserPost() throws IOException {

        HttpPost request = new HttpPost("http://localhost:8080/ClanGame-1.0-SNAPSHOT/users");
        StringEntity params = new StringEntity("{\"name\":\"TEST USER\",\"surname\":\"IVANOV\"," +
                "\"clan_id\":\"1\"} ");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        MatcherAssert.assertThat(httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_CREATED));
    }

    @Test
    @Order(5)
    @DisplayName("Check added User")
    public void testIfUserAddedCorrectly() throws IOException{

        HttpUriRequest request = new HttpGet("http://localhost:8080/ClanGame-1.0-SNAPSHOT/users/7");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        MatcherAssert.assertThat(httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));

        String json_string = EntityUtils.toString(httpResponse.getEntity());
        JSONObject user = new JSONObject(json_string);

        assertEquals(user.get("name"), "TEST USER");
        assertEquals(user.get("surname"), "IVANOV");

    }



}
