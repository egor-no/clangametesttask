package clangame.taskservice;

import clangame.clan.Clan;
import clangame.clanservice.ClanService;

public class UserAddGoldService { // пользователь добавляет золото из собственного кармана

    private final ClanService clans;

    public UserAddGoldService(ClanService clans) {
        this.clans = clans;
    }

    public void addGoldToClan(long userId, long clanId, int gold) {
        Clan clan = clans.get(clanId);
        // clangame.clan.[gold] += gold;
        // как-то сохранить изменения
    }
}
