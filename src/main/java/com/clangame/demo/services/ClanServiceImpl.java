package com.clangame.demo.services;

import com.clangame.demo.data.dao.ClanDAO;
import com.clangame.demo.data.entities.Clan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClanServiceImpl implements ClanService {

    @Inject
    private ClanDAO clanDAO;

    @Override
    public Clan get(long clanId) {
        Clan orElse = new Clan(clanId, "test", 100);
        System.out.println((clanDAO == null) + "  ATTETNTION!!!!");
        return clanDAO.get(clanId).orElse(orElse);
    }

    @Override
    public void save(Clan clan) {

    }
}
