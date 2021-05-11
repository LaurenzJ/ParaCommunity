package com.ichlebimaldi.paracommunity.sql;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLGetter {
    private ParaCommunity plugin;

    public SQLGetter(ParaCommunity plugin) {
        this.plugin = plugin;
    }

    public void createTable(){
        PreparedStatement ps;
        try {
            ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS CommunityClaims"
                                                              + "(CommunityName VARCHAR(100), UUID VARCHAR(100), " +
                                                                "WORLD VARCHAR(100), X1 DOUBLE, Z1 DOUBLE, X2 DOUBLE, Z2 DOUBLE, PRIMARY KEY (CommunityName))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCommunity(Player player, String communityName){
        try {
            if(!exists(communityName)){
                PreparedStatement ps2 = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO CommunityClaims (CommunityName, UUID) VALUES (?,?)");
                ps2.setString(1, communityName);
                ps2.setString(2, player.getUniqueId().toString());
                ps2.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(String communityName) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM CommunityClaims WHERE CommunityName=?");
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

    public void addFirstPoint(Location loc) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE CommunityClaims SET X1=?, Z1=?");
            ps.setString(1, String.valueOf(loc.getX()));
            ps.setString(2, String.valueOf(loc.getZ()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSecondPoint(Location loc) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE CommunityClaims SET X2=?, Z2=?");
            ps.setString(1, String.valueOf(loc.getX()));
            ps.setString(2, String.valueOf(loc.getZ()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Location getFirstLocation(){
        Location loc = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT WORLD, X1, Z1 FROM CommunityClaims");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String worldName = rs.getString("WORLD");
                World world = Bukkit.getWorld(worldName);
                double x = rs.getDouble("X1");
                double z = rs.getDouble("Z1");
                double y = 71;
                loc = new Location(world, x, y, z);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loc;
    }

    public Location getSecondLocation(){
        Location loc = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT WORLD, X2, Z2 FROM CommunityClaims");
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

}
