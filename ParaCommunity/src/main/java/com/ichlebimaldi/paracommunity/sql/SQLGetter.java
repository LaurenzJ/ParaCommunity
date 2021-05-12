package com.ichlebimaldi.paracommunity.sql;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGetter {
    private ParaCommunity plugin;

    public SQLGetter(ParaCommunity plugin) {
        this.plugin = plugin;
    }

    public void createCommunitesTable(){
        PreparedStatement ps;
        try {
            ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Communities"
                                                              + "(CommunityName VARCHAR(100) UNIQUE, OwnerUUID VARCHAR(100), " +
                                                                "WORLD VARCHAR(100), X1 DOUBLE, Z1 DOUBLE, X2 DOUBLE, Z2 DOUBLE, " +
                                                                "X3 DOUBLE, Z3 DOUBLE, X4 DOUBLE, Z4 DOUBLE,PRIMARY KEY (CommunityName))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable(){
        PreparedStatement ps;
        try {
            ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS Users"
                    + "(CommunityName VARCHAR(100), UUID VARCHAR(100) UNIQUE, PLAYERNAME VARCHAR(100), PRIMARY KEY (PLAYERNAME))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String communityName, Player player){
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE Users SET CommunityName=?, UUID=?, PLAYERNAME=?");
            ps.setString(1, communityName);
            ps.setString(2, player.getUniqueId().toString());
            ps.setString(3, player.getDisplayName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCommunity(Player player, String communityName){
        try {
            if(!existsCommunity(communityName)){
                PreparedStatement ps2 = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO Communities (CommunityName, OwnerUUID) VALUES (?,?)");
                ps2.setString(1, communityName);
                ps2.setString(2, player.getUniqueId().toString());
                ps2.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createUser(Player player){
        try {
            if(!existsUser(player)){
                PreparedStatement ps2 = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO Users (UUID, PLAYERNAME) VALUES (?,?)");
                ps2.setString(1, player.getUniqueId().toString());
                ps2.setString(2, player.getDisplayName());
                ps2.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean existsCommunity(String communityName) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM Communities WHERE CommunityName=?");
            ps.setString(1, communityName);

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsUser(Player player) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM Users WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addFirstLocation(Location loc, Player player, String communityName) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE Communities SET X1=?, Z1=?, WORLD=? WHERE CommunityName=?");
            ps.setString(1, String.valueOf(loc.getX()));
            ps.setString(2, String.valueOf(loc.getZ()));
            ps.setString(3, player.getWorld().getName());
            ps.setString(4, communityName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSecondLocation(Location loc2, Location loc3, Location loc4, Player player, String communityName) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE Communities SET X2=?, Z2=?, WORLD=? WHERE CommunityName=?");
            ps.setString(1, String.valueOf(loc2.getX()));
            ps.setString(2, String.valueOf(loc2.getZ()));
            ps.setString(3, player.getWorld().getName());
            ps.setString(4, communityName);
            //calculate other points here
            /*ps.setString(3, String.valueOf(loc.getZ()));
            ps.setString(4, String.valueOf(loc.getZ()));*/
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Location getFirstLocation(String communityName){
        Location loc = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT WORLD, X1, Z1 FROM Communities WHERE CommunityName=?");
            ps.setString(1, communityName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String worldName = rs.getString("WORLD");

                if(!rs.wasNull()) {
                    World world = Bukkit.getWorld(worldName);
                    double x = rs.getDouble("X1");
                    double z = rs.getDouble("Z1");
                    double y = 71;
                    if (world != null) {
                        return new Location(world, x, y, z);
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loc;
    }

    public Location getSecondLocation(String communityName){
        Location loc = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT WORLD, X2, Z2 FROM Communities WHERE CommunityName=?");
            ps.setString(1, communityName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String worldName = rs.getString("WORLD");
                World world = Bukkit.getWorld(worldName);
                double x = rs.getDouble("X2");
                double z = rs.getDouble("Z2");
                double y = 71;
                loc = new Location(world, x, y, z);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loc;
    }

    public World getWorld(String communityName){
        World world = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT WORLD, X2, Z2 FROM Communities WHERE CommunityName=?");
            ps.setString(1, communityName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String worldName = rs.getString("WORLD");
                world = Bukkit.getWorld(worldName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return world;
    }

    public List<Player> getCommunityMembers(String communityName) {
        List<Player> members = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT PLAYERNAME FROM Users WHERE CommunityName=?");
            ps.setString(1, communityName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String playername = rs.getString("PLAYERNAME");
                members.add(Bukkit.getPlayer(playername));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public String getCommunity(Player player){
        String communityName = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT CommunityName FROM Users WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                communityName = rs.getString("CommunityName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return communityName;
    }
}
