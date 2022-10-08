package clangame.data.dao;

import clangame.data.db.H2Connector;
import clangame.data.entities.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClanDAO implements DAO<Clan> {

    @Override
    public Optional<Clan> get(long id) {
        String sql = "SELECT clan_id, name, gold FROM clan WHERE id=?";

        Clan clan = null;
        try (Connection connection = H2Connector.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            clan = new Clan();
            while (rs.next()) {
                clan.setId(rs.getLong("clan_id"));
                clan.setName(rs.getString("name"));
                clan.setGold(rs.getInt("gold"));
            }

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
