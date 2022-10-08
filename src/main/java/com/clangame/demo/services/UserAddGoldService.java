package com.clangame.demo.services;

import com.clangame.demo.data.entities.Clan;

public class UserAddGoldService { // пользователь добавляет золото из собственного кармана

    private final ClanService clans;

    public UserAddGoldService(ClanService clans) {
        this.clans = clans;
    }

    public void addGoldToClan(long userId, long clanId, int gold) {
        Clan clan = clans.get(clanId);
        // clangame.clan.[gold] += gold;
        // как-то сохранить изменения

        //transactiondao
        //usertransaction dao
    }
}
