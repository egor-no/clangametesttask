package com.clangame.demo.services;

import com.clangame.demo.data.dao.ClanDAO;
import com.clangame.demo.data.entities.Clan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
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
