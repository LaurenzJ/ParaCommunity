package com.ichlebimaldi.paracommunity.sql;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.claim.Area;
import com.ichlebimaldi.paracommunity.communities.Community;
import com.ichlebimaldi.paracommunity.communities.Guild;
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
                                                                "WORLD VARCHAR(100), X1 DOUBLE, Y1 DOUBLE, Z1 DOUBLE, X2 DOUBLE, Y2 DOUBLE, Z2 DOUBLE, " +
                                                                "X3 DOUBLE, Y3 DOUBLE, Z3 DOUBLE, X4 DOUBLE, Y4 DOUBLE, Z4 DOUBLE,PRIMARY KEY (CommunityName))");
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

    public boolean createCommunity(Player player, Community community){
        try {
            if(!existsCommunity(community.getName())){
                PreparedStatement ps1 = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO Communities (CommunityName, OwnerUUID) VALUES (?,?)");
                ps1.setString(1, community.getName());
                ps1.setString(2, player.getUniqueId().toString());
                ps1.executeUpdate();
                PreparedStatement ps2 = plugin.sql.getConnection().prepareStatement("UPDATE Users SET CommunityName=? WHERE UUID=?");
                ps2.setString(1, community.getName());
                ps2.setString(2, player.getUniqueId().toString());
                ps2.executeUpdate();
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
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
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE Communities SET X1=?, Y1=?, Z1=?, WORLD=? WHERE CommunityName=?");
            ps.setString(1, String.valueOf(loc.getX()));
            ps.setString(1, String.valueOf(loc.getY()));
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
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("UPDATE Communities SET X2=?, Y2=?, Z2=?, X3=?, Y3=?, Z3=?, X4=?, Y4=?, Z4=?, WORLD=? WHERE CommunityName=?");
            ps.setString(1, String.valueOf(loc2.getX()));
            ps.setString(1, String.valueOf(loc2.getY()));
            ps.setString(2, String.valueOf(loc2.getZ()));
            ps.setString(3, String.valueOf(loc3.getX()));
            ps.setString(3, String.valueOf(loc3.getY()));
            ps.setString(4, String.valueOf(loc3.getZ()));
            ps.setString(5, String.valueOf(loc4.getX()));
            ps.setString(5, String.valueOf(loc4.getY()));
            ps.setString(6, String.valueOf(loc4.getZ()));
            ps.setString(7, String.valueOf(loc2.getWorld().getName()));
            ps.setString(8, communityName);
            /*ps.setString(3, String.valueOf(loc.getZ()));
            ps.setString(4, String.valueOf(loc.getZ()));*/
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Location getFirstLocation(Community community){
        Location loc = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT WORLD, X1, Y2, Z1 FROM Communities WHERE CommunityName=?");
            ps.setString(1, community.getName());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String worldName = rs.getString("WORLD");

                if(!rs.wasNull()) {
                    World world = Bukkit.getWorld(worldName);
                    double x = rs.getDouble("X1");
                    double y = rs.getDouble("Y1");
                    double z = rs.getDouble("Z1");

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

    public Location getSecondLocation(Community community){
        Location loc = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT WORLD, X2, Y2, Z2,  FROM Communities WHERE CommunityName=?");
            ps.setString(1, community.getName());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String worldName = rs.getString("WORLD");
                World world = Bukkit.getWorld(worldName);
                double x = rs.getDouble("X2");
                double y = rs.getDouble("Y2");
                double z = rs.getDouble("Z2");

                loc = new Location(world, x, y, z);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loc;
    }

    public Area getArea(Community community){
        Area area = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT *  FROM Communities WHERE CommunityName=?");
            ps.setString(1, community.getName());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String worldName = rs.getString("WORLD");
                World world = Bukkit.getWorld(worldName);
                double x1 = rs.getDouble("X1");
                double y1 = rs.getDouble("Y1");
                double z1 = rs.getDouble("Z1");

                double x2 = rs.getDouble("X2");
                double y2 = rs.getDouble("Y2");
                double z2 = rs.getDouble("Z2");

                double x3 = rs.getDouble("X3");
                double y3 = rs.getDouble("Y3");
                double z3 = rs.getDouble("Z3");

                double x4 = rs.getDouble("X4");
                double y4 = rs.getDouble("Y4");
                double z4 = rs.getDouble("Z4");

                Location loc1 = new Location(world, x1, y1, z1);
                Location loc2 = new Location(world, x2, y2, z2);
                Location loc3 = new Location(world, x3, y3, z3);
                Location loc4 = new Location(world, x4, y4, z4);

                area = new Area();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return area;
    }

    public World getWorld(Community community){
        World world = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT WORLD, X2, Z2 FROM Communities WHERE CommunityName=?");
            ps.setString(1, community.getName());
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

    public List<Player> getCommunityMembers(Community community) {
        List<Player> members = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT PLAYERNAME FROM Users WHERE CommunityName=?");
            ps.setString(1, community.getName());
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

    public Community getCommunity(Player player){
        Community community = null;
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT CommunityName FROM Users WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                community = new Guild(rs.getString("CommunityName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return community;
    }

    public void setSQL(PreparedStatement ps){
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getSQL(PreparedStatement ps, String columnLabel){
        try {
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString(columnLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
