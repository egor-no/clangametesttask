package testThreads;

import com.clangame.demo.services.ClanService;
import com.clangame.demo.services.TaskService;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskServiceThread extends Thread {

    private final CyclicBarrier gate;

    public TaskServiceThread(CyclicBarrier gate) {
        super();
        this.gate = gate;
    }

    @SneakyThrows
    @Override
    public void run() {
        gate.await();
        JSONObject inner = null;
        do {
            HttpUriRequest request = new HttpPut("http://localhost:8080/ClanGame-1.0-SNAPSHOT/task/1/complete?clan=1");
            HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

            try {
                String json_string = null;
                json_string = EntityUtils.toString(httpResponse.getEntity());
                JSONObject transaction = new JSONObject(json_string);
                inner = transaction.getJSONObject("transaction");
            } catch (org.json.JSONException e) {
            }
        } while (inner == null || inner.get("successful").equals("true"));

    }
}
