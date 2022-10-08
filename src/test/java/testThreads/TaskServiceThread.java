package testThreads;

import com.clangame.demo.services.ClanService;
import com.clangame.demo.services.TaskService;

import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskServiceThread extends Thread {

    private final ClanService clanService;
    private final CyclicBarrier gate;

    public TaskServiceThread(ClanService clanService, CyclicBarrier gate) {
        super();
        this.clanService = clanService;
        this.gate = gate;
    }

    @Override
    public void run() {
        TaskService taskService = new TaskService(clanService);
        taskService.completeTask(1, 1);
    }
}
