package com.ichlebimaldi.paracommunity.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        PlayerInventory inv = player.getInventory();
        ItemStack itemStack = new ItemStack(Material.GOLDEN_SHOVEL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§5Claim");
        itemStack.setItemMeta(itemMeta);
        inv.setItem(0, itemStack);
        inv.setHeldItemSlot(0);
        Location loc = player.getLocation();
        player.sendMessage(loc.toString());
        return false;
    }
}
