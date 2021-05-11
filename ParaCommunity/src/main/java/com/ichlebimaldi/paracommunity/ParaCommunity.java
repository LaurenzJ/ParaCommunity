package com.ichlebimaldi.paracommunity;

import com.ichlebimaldi.paracommunity.commands.ClaimCommand;
import com.ichlebimaldi.paracommunity.listeners.ClaimEvent;
import com.ichlebimaldi.paracommunity.sql.MySQL;
import com.ichlebimaldi.paracommunity.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class ParaCommunity extends JavaPlugin {

    public MySQL sql;
    public SQLGetter data;

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
            data.createTable();
        }


        getCommand("claim").setExecutor(new ClaimCommand());
        new ClaimEvent(this);

    }

    @Override
    public void onDisable() {
        sql.disconnect();
    }
}
