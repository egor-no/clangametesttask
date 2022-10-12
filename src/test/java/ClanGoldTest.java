import com.clangame.demo.services.ClanService;
import com.clangame.demo.services.ClanServiceImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testThreads.TaskServiceThread;
import testThreads.UserAddGoldServiceThread;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

//тесты для проверки основной функции зачисления денег
public class ClanGoldTest {

    @Test
    @DisplayName("TaskService changes gold")
    public void testOneTaskService() throws IOException {

        Integer oldBalance = extractBalance();

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

        Integer newBalance = extractBalance();

        assertEquals(oldBalance+100, newBalance);
    }

    @Test
    @DisplayName("UserAddService changes gold")
    public void testOneUserAddGoldService() throws IOException {
        Integer oldBalance = extractBalance();

        HttpUriRequest request = new HttpPut("http://localhost:8080/ClanGame-1.0-SNAPSHOT/users/1/addmoney?gold=50&clan=1");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String json_string = EntityUtils.toString(httpResponse.getEntity());
        JSONObject transaction = new JSONObject(json_string);

        JSONObject inner = transaction.getJSONObject("transaction");
        assertEquals(50, inner.get("delta"));

        Integer newBalance = extractBalance();

        assertEquals(oldBalance+50, newBalance);
    }

    @Test
    @DisplayName("100 TaskServices change gold")
    public void testHundredTaskServices() throws IOException, InterruptedException {
        final CyclicBarrier gate = new CyclicBarrier(100);

        for (int i = 0 ; i < 100; i++) {
            new TaskServiceThread(gate).start();
        }
        try {
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        assertEquals(100*100, extractBalance());

    }

    @Test
    @DisplayName("100 UserAddServices change gold")
    public void testHundredUserAddGoldServices() throws IOException, InterruptedException {
        final CyclicBarrier gate = new CyclicBarrier(101);

        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0; i<100; i++)
            es.execute( new UserAddGoldServiceThread(gate));

        try {
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        es.shutdown();

        boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(5000, extractBalance());
    }

    @Test
    @DisplayName("100 Random Services change gold")
    public void testHundredRandomServices() throws IOException {
        ClanService clanService = new ClanServiceImpl();
        final CyclicBarrier gate = new CyclicBarrier(100);

        for (int i = 0 ; i < 100; i++) {
            int r = new Random().nextInt(2);
            if (r==1)
                new TaskServiceThread(gate).start();
            else
                new UserAddGoldServiceThread(gate).start();
        }
        try {
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        assertEquals(100*100, extractBalance());
    }

    private Integer extractBalance() throws IOException {
        HttpUriRequest clanRequest = new HttpGet("http://localhost:8080/ClanGame-1.0-SNAPSHOT/clan/1");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(clanRequest);
        String json_string = EntityUtils.toString(httpResponse.getEntity());
        JSONObject clan = new JSONObject(json_string);
        return (Integer) clan.get("gold");
    }
}
