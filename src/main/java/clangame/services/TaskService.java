package clangame.services;

import clangame.data.entities.Clan;
import clangame.services.ClanService;

public class TaskService { // какой-то сервис с заданиями

    private final ClanService clans;

    public TaskService(ClanService clans) {
        this.clans = clans;
    }

    public void completeTask(long clanId, long taskId) {
        // ...

        // if (success)
        {
            Clan clan = clans.get(clanId);
            // clangame.clan.[gold] += gold;
            // как-то сохранить изменения
        }
    }
}