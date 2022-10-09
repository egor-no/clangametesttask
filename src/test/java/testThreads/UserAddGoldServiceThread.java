package testThreads;

import com.clangame.demo.services.ClanService;
import com.clangame.demo.services.UserAddGoldService;

import java.util.concurrent.CyclicBarrier;

public class UserAddGoldServiceThread extends Thread  {

    private final ClanService clanService;
    private final CyclicBarrier gate;


    public UserAddGoldServiceThread(ClanService clanService, CyclicBarrier gate) {
        super();
        this.clanService = clanService;
        this.gate = gate;
    }

    @Override
    public void run() {
        //UserAddGoldService userAddGoldService = new UserAddGoldService(clanService);
        //userAddGoldService.addGoldToClan(1,1, 100);
    }
}
