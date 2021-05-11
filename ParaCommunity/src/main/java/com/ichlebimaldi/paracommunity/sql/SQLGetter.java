package com.ichlebimaldi.paracommunity.sql;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import org.bukkit.Location;

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

    public void createCommunity(String communityName){
        try {
            if(!exists(communityName)){
                PreparedStatement ps2 = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO CommunityClaims CommunityName VALUE ?");
                ps2.setString(1, communityName);
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
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
