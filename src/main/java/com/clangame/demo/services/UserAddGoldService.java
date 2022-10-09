package com.clangame.demo.services;

import com.clangame.demo.data.entities.Clan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserAddGoldService { // пользователь добавляет золото из собственного кармана

    @Inject
    private ClanService clans;

    public void addGoldToClan(long userId, long clanId, int gold) {
        Clan clan = clans.get(clanId);
        // clangame.clan.[gold] += gold;
        // как-то сохранить изменения

        //transactiondao
        //usertransaction dao
    }
}
