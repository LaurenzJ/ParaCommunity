package com.ichlebimaldi.paracommunity.claim;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.communities.Community;
import com.ichlebimaldi.paracommunity.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Area {
    SQLGetter data = ParaCommunity.data;

    private Location loc1;
    private Location loc2;
    private Location loc3;
    private Location loc4;

    private Community community;


    public void setArea(Community community, Location loc1, Location loc2, Location loc3, Location loc4){
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.loc3 = loc3;
        this.loc4 = loc4;

        try {
            PreparedStatement ps = ParaCommunity.sql.getConnection().prepareStatement("UPDATE Communities SET X2=?, " +
                                                                                    "Z2=?, X3=?, Z3=?, X4=?, Z4=?, " +
                                                                                    "WORLD=? WHERE CommunityName=?");
            ps.setString(1, String.valueOf(loc2.getX()));
            ps.setString(2, String.valueOf(loc2.getZ()));
            ps.setString(3, String.valueOf(loc3.getX()));
            ps.setString(4, String.valueOf(loc3.getZ()));
            ps.setString(5, String.valueOf(loc4.getX()));
            ps.setString(6, String.valueOf(loc4.getZ()));
            ps.setString(7, String.valueOf(loc2.getWorld().getName()));
            ps.setString(8, community.getName());
            data.setSQL(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setLoc1(Community community, Location loc1, Player player) {
        this.loc1 = loc1;

        try {
            PreparedStatement ps = ParaCommunity.sql.getConnection().prepareStatement("UPDATE Communities SET X1=?, Y1=?," +
                                                                                    "Z1=?, WORLD=? WHERE CommunityName=?");
            ps.setString(1, String.valueOf(loc1.getX()));
            ps.setString(1, String.valueOf(loc1.getY()));
            ps.setString(2, String.valueOf(loc1.getZ()));
            ps.setString(3, player.getLocation().getWorld().getName());
            ps.setString(4, community.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLoc2(Community community, Location loc2, Player player) {
        retrieveLocs(community);
        System.out.println("YYEEET" + loc1);
        this.loc2 = loc2;
        this.loc3.setX(this.loc1.getX());
        this.loc3.setZ(this.loc2.getZ());

        this.loc4.setX(loc2.getX());
        this.loc4.setZ(loc1.getZ());

        try {
            PreparedStatement ps = ParaCommunity.sql.getConnection().prepareStatement("UPDATE Communities SET X2=?, Y2=?, " +
                                                                                    "Z2=?, X3=?, Y3=?, Z3=?, X4=?, Y4=?, Z4=?, " +
                                                                                    "WORLD=? WHERE CommunityName=?");
            ps.setString(1, String.valueOf(loc2.getX()));
            ps.setString(2, String.valueOf(loc2.getY()));
            ps.setString(3, String.valueOf(loc2.getZ()));
            ps.setString(4, String.valueOf(loc3.getX()));
            ps.setString(5, String.valueOf(loc3.getY()));
            ps.setString(6, String.valueOf(loc3.getZ()));
            ps.setString(7, String.valueOf(loc4.getX()));
            ps.setString(8, String.valueOf(loc4.getY()));
            ps.setString(9, String.valueOf(loc4.getZ()));
            ps.setString(10, player.getLocation().getWorld().getName());
            ps.setString(11, community.getName());
            data.setSQL(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retrieveLocs(Community community) {
            try {
                PreparedStatement ps = ParaCommunity.sql.getConnection().prepareStatement("SELECT * FROM Communities WHERE CommunityName=?");
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
                    double y = 71;
                    if (world != null) {
                        loc1 = new Location(world, x1, y1, z1);
                        loc2 = new Location(world, x2, y2, z2);
                        loc3 = new Location(world, x3, y3, z3);
                        loc4 = new Location(world, x4, y4, z4);
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public Location getLoc3() {
        return loc3;
    }

    public Location getLoc4() {
        return loc4;
    }
}
