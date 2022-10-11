package com.clangame.demo.data.dao;

import com.clangame.demo.data.db.H2Connector;
import com.clangame.demo.data.entities.Clan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClanDAO implements DAO<Clan, Long> {

    @Inject
    private H2Connector connector;

    @Override
    public Optional<Clan> get(Long id) {
        String sql = "SELECT * FROM clan WHERE clan_id=?";

        Clan clan = null;
        try (Connection connection = connector.getConnection()){
            System.out.println(connection.isClosed() + " ");
            PreparedStatement ps = connector.getConnection().prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clan = new Clan();
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
        String sql = "SELECT * FROM clan";
        List<Clan> clans = new ArrayList<>();
        try (Connection connection = connector.getConnection()){ ;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Clan clan = new Clan();
                clan.setId(rs.getLong("clan_id"));
                clan.setName(rs.getString("name"));
                clan.setGold(rs.getInt("gold"));
                clans.add(clan);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return clans;
    }

    @Override
    public void save(Clan clan) {
        String insertQuery = "INSERT INTO clan " + "(name, gold) VALUES " + "(?,?)";
        try (Connection connection = connector.getConnection()) {
            PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
            insertPreparedStatement.setString(1, clan.getName());
            insertPreparedStatement.setInt(2, clan.getGold());
            insertPreparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Clan clan) {
        String updateQuery = "UPDATE clan SET name=?, gold=? "
                + "WHERE clan_id=?";
        try (Connection connection = connector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, clan.getName());
            statement.setInt(2, clan.getGold());
            System.out.println("!!!!!!!!!!!!!!!!! " + clan.getGold() + " !!!!!!!!!!");
            statement.setLong(3, clan.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM clan WHERE clan_id=?";
        try (Connection connection = connector.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
