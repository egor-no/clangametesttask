package clangame.services;

import clangame.data.dao.ClanDAO;
import clangame.data.entities.Clan;

import javax.inject.Inject;

public class ClanServiceImpl implements ClanService {

    @Inject
    ClanDAO clanDAO;

    @Override
    public Clan get(long clanId) {
        return clanDAO.get(clanId).orElse(null);
    }

    @Override
    public void save(Clan clan) {

    }
}
