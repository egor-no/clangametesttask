package com.clangame.demo.data.dao;

import com.clangame.demo.data.db.H2Connector;
import com.clangame.demo.data.entities.Clan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClanDAO implements DAO<Clan> {

    @Inject
    private H2Connector connector;

    @Override
    public Optional<Clan> get(long id) {
        String sql = "SELECT * FROM clan WHERE clan_id=?";
        System.out.println("here");
        Clan clan = new Clan(0, "test", 100);
        try (Connection connection = connector.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                clan.setId(rs.getLong("clan_id"));
                clan.setName(rs.getString("name"));
                clan.setGold(rs.getInt("gold"));
            }

            System.out.println(clan.getGold()+" "+clan.getId()+ " "+ clan.getName());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.ofNullable(clan);
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
