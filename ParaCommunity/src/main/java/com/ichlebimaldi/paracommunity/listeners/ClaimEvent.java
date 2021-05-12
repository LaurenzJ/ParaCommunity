package com.ichlebimaldi.paracommunity.listeners;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.sql.SQLGetter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
        String communityName = data.getCommunity(player);
        Action action = event.getAction();
        ItemStack item = event.getItem();

        Block block = player.getTargetBlock(null, 100);
        if(item != null && item.getItemMeta().getDisplayName().equals("§5Claim")){
            if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                if(data.getFirstLocation("Temp2") != null) {
                    player.sendBlockChange(data.getFirstLocation(communityName), data.getFirstLocation(communityName).getBlock().getBlockData().getMaterial(), (byte) 0);
                    player.sendBlockChange(data.getSecondLocation(communityName), data.getSecondLocation(communityName).getBlock().getBlockData().getMaterial(), (byte) 0);





                    data.addFirstLocation(block.getLocation(), player, communityName);

                } else {
                    data.addFirstLocation(block.getLocation(), player, communityName);
                }
                event.setCancelled(true);

            } else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                double bef_x1 = data.getFirstLocation(communityName).getX();
                double bef_z1 = data.getFirstLocation(communityName).getZ();
                double bef_x2 = data.getSecondLocation(communityName).getX();
                double bef_z2 = data.getSecondLocation(communityName).getZ();

                double bef_x3 = bef_x1;
                double bef_z3 = bef_z2;
                Location loc3 = new Location(block.getWorld(), bef_x3, 71,bef_z3);


                double bef_x4 = bef_x2;
                double bef_z4 = bef_z1;
                Location loc4 = new Location(block.getWorld(), bef_x4, 71,bef_z4);



                if(data.getFirstLocation(communityName) != null) {
                    double distance = loc4.distance(data.getFirstLocation(communityName));
                    player.sendMessage("Distance loc4 - loc1: " + distance);

                    player.sendBlockChange(new Location(player.getWorld(), bef_x3, 71, bef_z3), Material.BLUE_CONCRETE, (byte) 0);
                    player.sendBlockChange(new Location(player.getWorld(), bef_x4, 71, bef_z4), Material.BLUE_CONCRETE, (byte) 0);

                    player.sendBlockChange(data.getFirstLocation(communityName), data.getFirstLocation(communityName).getBlock().getBlockData().getMaterial(),(byte) 0);
                    player.sendBlockChange(data.getSecondLocation(communityName), data.getSecondLocation(communityName).getBlock().getBlockData().getMaterial(),(byte) 0);
                    data.addSecondLocation(block.getLocation(), loc3, loc4, player, communityName);
                } else {
                    data.addSecondLocation(block.getLocation(), loc3, loc4, player, communityName);
                }


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
            player.sendMessage(data.getFirstLocation("Temp2").toString());
            player.sendBlockChange(data.getFirstLocation("Temp2"), Material.RED_STAINED_GLASS, (byte) 14); //14 --> color: red;
            player.sendBlockChange(data.getSecondLocation("Temp2"), Material.RED_STAINED_GLASS, (byte) 14); //14 --> color: red;
        } else {
            player.sendBlockChange(data.getFirstLocation("Temp2"), data.getFirstLocation("Temp2").getBlock().getBlockData().getMaterial(),(byte) 0);
            player.sendBlockChange(data.getSecondLocation("Temp2"), data.getSecondLocation("Temp2").getBlock().getBlockData().getMaterial(),(byte) 0);
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
