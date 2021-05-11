package com.ichlebimaldi.paracommunity.listeners;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClaimEvent implements Listener {

    public ClaimEvent(ParaCommunity plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerClicked(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        Block block = player.getTargetBlock(null, 100);
        if ((action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_BLOCK)) && item != null && item.getItemMeta().getDisplayName().equals("§5Claim")) {
            player.sendMessage("x: " +block.getX());
            player.sendMessage("y: " +block.getY());
            player.sendMessage("z: " +block.getZ());
            event.setCancelled(true);
        }

    }
}
