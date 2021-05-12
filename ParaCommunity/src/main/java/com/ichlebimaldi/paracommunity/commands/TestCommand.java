package com.ichlebimaldi.paracommunity.commands;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.sql.SQLGetter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    SQLGetter data;
    ParaCommunity plugin;

    public TestCommand(ParaCommunity plugin) {
        this.plugin = plugin;
        this.data = new SQLGetter(this.plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            player.sendMessage(data.getCommunityMembers("Temp2").toString());
        }
        return false;
    }
}
