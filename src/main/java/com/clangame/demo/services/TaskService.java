package com.clangame.demo.services;

import com.clangame.demo.data.entities.Clan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TaskService { // какой-то сервис с заданиями

    @Inject
    private ClanService clans;

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