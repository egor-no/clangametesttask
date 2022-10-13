package com.clangame.demo.services;

import com.clangame.demo.data.dao.ClanDAO;
import com.clangame.demo.data.entities.Clan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ClanServiceImpl implements ClanService {

    @Inject
    private ClanDAO clanDAO;

    @Override
    public Clan get(long clanId) {
        return clanDAO.get(clanId).orElse(null);
    }

    @Override
    public List<Clan> getAll() {
        return clanDAO.getAll();
    }

    @Override
    public long save(Clan clan) {
        clanDAO.save(clan);
        long id = clanDAO.getAll().size();
        return id;
    }

    public void update(Clan clan) {
        clanDAO.update(clan);
    }

    public void delete(long clanId) {
        clanDAO.delete(clanId);
    }
}
