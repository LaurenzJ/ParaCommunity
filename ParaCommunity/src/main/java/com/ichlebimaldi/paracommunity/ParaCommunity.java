package com.ichlebimaldi.paracommunity;

import com.ichlebimaldi.paracommunity.commands.ClaimCommand;
import com.ichlebimaldi.paracommunity.commands.CommunityCommand;
import com.ichlebimaldi.paracommunity.commands.TestCommand;
import com.ichlebimaldi.paracommunity.listeners.*;
import com.ichlebimaldi.paracommunity.sql.MySQL;
import com.ichlebimaldi.paracommunity.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class ParaCommunity extends JavaPlugin {

    public static MySQL sql;
    public static SQLGetter data;

    public static final String PREFIX = "§7[§3Para§6Community§7]";

    @Override
    public void onEnable() {
        sql = new MySQL();
        data = new SQLGetter(this);
        try {
            sql.connect();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Database is not connected");
        }

        if(sql.isConnected()){
            Bukkit.getLogger().info("Database is connected");
            data.createUsersTable();
            data.createCommunitesTable();
            data.createUsersTable();
        }


        getCommand("claim").setExecutor(new ClaimCommand());
        getCommand("community").setExecutor(new CommunityCommand(this));
        getCommand("test").setExecutor(new TestCommand(this));

        new ClaimEvent(this);
        new JoinEvent(this);

    }

    @Override
    public void onDisable() {
        sql.disconnect();
    }

    public ParaCommunity getPlugin(){
        return this;
    }

}


