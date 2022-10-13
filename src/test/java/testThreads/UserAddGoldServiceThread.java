package testThreads;

import com.clangame.demo.services.ClanService;
import com.clangame.demo.services.UserAddGoldService;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.MatcherAssert;
import org.json.JSONObject;

import java.util.concurrent.CyclicBarrier;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAddGoldServiceThread extends Thread  {

    private final CyclicBarrier gate;

    public UserAddGoldServiceThread(CyclicBarrier gate) {
        super();
        this.gate = gate;
    }

    @SneakyThrows
    @Override
    public void run() {
        HttpResponse httpResponse = null;
        gate.await();
        do {
            HttpUriRequest request = new HttpPut("http://localhost:8080/ClanGame-1.0-SNAPSHOT/users/1/addmoney?gold=50&clan=1");
            httpResponse = HttpClientBuilder.create().build().execute(request);

        } while (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
}