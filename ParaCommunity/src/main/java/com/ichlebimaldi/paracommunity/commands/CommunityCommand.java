package com.ichlebimaldi.paracommunity.commands;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.communities.Community;
import com.ichlebimaldi.paracommunity.communities.Guild;
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
                sender.sendMessage(ParaCommunity.PREFIX + "§a/community create <CommunityName>");
                return true;
            }

            if(args[0].equalsIgnoreCase("create")){
                Guild community = new Guild(args[1]);
                if(data.createCommunity(player, community)){
                    player.sendMessage(ParaCommunity.PREFIX + "§aCreated Community");
                } else {
                    player.sendMessage(ParaCommunity.PREFIX + "§cName already taken!");
                }

            }


        }
        return false;
    }
}
