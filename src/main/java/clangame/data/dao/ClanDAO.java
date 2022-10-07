package clangame.data.dao;

import clangame.data.entities.Clan;

import java.util.List;
import java.util.Optional;

public class ClanDAO implements DAO<Clan> {

    @Override
    public Optional<Clan> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Clan> getAll() {
        return null;
    }

    @Override
    public void save(Clan clan) {

    }

    @Override
    public void update(Clan clan, String[] params) {

    }

    @Override
    public void delete(Clan clan) {

    }
}
