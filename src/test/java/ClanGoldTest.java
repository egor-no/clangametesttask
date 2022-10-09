import com.clangame.demo.services.ClanService;
import com.clangame.demo.services.ClanServiceImpl;
import com.clangame.demo.services.TaskService;
import com.clangame.demo.services.UserAddGoldService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testThreads.TaskServiceThread;
import testThreads.UserAddGoldServiceThread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.assertEquals;

//тесты для проверки основной функции зачисления денег
public class ClanGoldTest {

    @Test
    @DisplayName("TaskService changes gold")
    public void testOneTaskService() {
        ClanService clanService = new ClanServiceImpl();
        TaskService taskService = new TaskService(clanService);
        taskService.completeTask(1, 1);

        assertEquals(0, clanService.get(1).getGold());
    }

    @Test
    @DisplayName("UserAddService changes gold")
    public void testOneUserAddGoldService() {
        ClanService clanService = new ClanServiceImpl();
        UserAddGoldService userAddGoldService = new UserAddGoldService(clanService);
        userAddGoldService.addGoldToClan(1,1, 100);;

        assertEquals(100, clanService.get(1).getGold());
    }

    @Test
    @DisplayName("100 TaskServices change gold")
    public void testHundredTaskServices() {
        ClanService clanService = new ClanServiceImpl();
        final CyclicBarrier gate = new CyclicBarrier(100);

        for (int i = 0 ; i < 100; i++) {
            new TaskServiceThread(clanService, gate).start();
        }
        try {
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        assertEquals(100*100, clanService.get(1).getGold());

    }

    @Test
    @DisplayName("100 UserAddServices change gold")
    public void testHundredUserAddGoldServices() {
        ClanService clanService = new ClanServiceImpl();
        final CyclicBarrier gate = new CyclicBarrier(100);

        for (int i = 0 ; i < 100; i++) {
            new UserAddGoldServiceThread(clanService, gate).start();
        }
        try {
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        assertEquals(100*100, clanService.get(1).getGold());
    }

    @Test
    @DisplayName("100 Random Services change gold")
    public void testHundredRandomServices() {
        ClanService clanService = new ClanServiceImpl();
        final CyclicBarrier gate = new CyclicBarrier(100);

        for (int i = 0 ; i < 100; i++) {
            int r = new Random().nextInt(2);
            if (r==1)
                new TaskServiceThread(clanService, gate).start();
            else
                new UserAddGoldServiceThread(clanService, gate).start();
        }
        try {
            gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        assertEquals(100*100, clanService.get(1).getGold());
    }


}
