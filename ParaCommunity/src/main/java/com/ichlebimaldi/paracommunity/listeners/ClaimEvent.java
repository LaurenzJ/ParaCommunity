package com.ichlebimaldi.paracommunity.listeners;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.sql.SQLGetter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClaimEvent implements Listener {

    private SQLGetter data;

    public ClaimEvent(ParaCommunity plugin) {
        data = new SQLGetter(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerClicked(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        Block block = player.getTargetBlock(null, 100);
        if(item != null && item.getItemMeta().getDisplayName().equals("§5Claim")){
            if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                data.createCommunity(player, "TestCommunity");
                player.sendBlockChange(data.getFirstLocation(), data.getFirstLocation().getBlock().getBlockData().getMaterial(),(byte) 0);
                player.sendBlockChange(data.getSecondLocation(), data.getSecondLocation().getBlock().getBlockData().getMaterial(),(byte) 0);
                data.addFirstPoint(block.getLocation());

                event.setCancelled(true);

            } else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                data.createCommunity(player, "TestCommunity");
                player.sendBlockChange(data.getFirstLocation(), data.getFirstLocation().getBlock().getBlockData().getMaterial(),(byte) 0);
                player.sendBlockChange(data.getSecondLocation(), data.getSecondLocation().getBlock().getBlockData().getMaterial(),(byte) 0);
                data.addSecondPoint(block.getLocation());

                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        Inventory inv = player.getInventory();
        ItemStack item = inv.getItem(event.getNewSlot());
        if (item != null && item.getItemMeta().getDisplayName().equals("§5Claim")){
            player.sendBlockChange(data.getFirstLocation(), Material.RED_STAINED_GLASS, (byte) 14); //14 --> color: red;
            player.sendBlockChange(data.getSecondLocation(), Material.RED_STAINED_GLASS, (byte) 14); //14 --> color: red;
        } else {
            player.sendBlockChange(data.getFirstLocation(), data.getFirstLocation().getBlock().getBlockData().getMaterial(),(byte) 0);
            player.sendBlockChange(data.getSecondLocation(), data.getSecondLocation().getBlock().getBlockData().getMaterial(),(byte) 0);
        }


    }



//    @EventHandler
//    public void onPlayerDropItem(PlayerDropItemEvent event) {
//        ItemStack item = event.getItemDrop().getItemStack();
//        Player player = event.getPlayer();
//        Inventory inv = player.getInventory();
//
//        if(item.getItemMeta().getDisplayName().equals("§5Claim")) {
//            inv.removeItem(item);
//            event.setCancelled(true);
//        }
//
//    }
}
