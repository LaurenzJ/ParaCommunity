package com.ichlebimaldi.paracommunity.commands;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.sql.SQLGetter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommunityCommand implements CommandExecutor {
    private SQLGetter data;
    private ParaCommunity plugin;

    public CommunityCommand(ParaCommunity plugin) {
        this.plugin = plugin;
        this.data = new SQLGetter(this.plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length <= 1) {
                sender.sendMessage("§a/community create <CommunityName>");
                return true;
            }

            if(args[0].equalsIgnoreCase("create")){
                data.createCommunity(player, args[1]);
                player.sendMessage("§aUse /claim to claim your first area!");
            }


        }
        return false;
    }
}
