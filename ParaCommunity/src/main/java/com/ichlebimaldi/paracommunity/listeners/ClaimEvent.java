package com.ichlebimaldi.paracommunity.listeners;

import com.ichlebimaldi.paracommunity.ParaCommunity;
import com.ichlebimaldi.paracommunity.claim.Area;
import com.ichlebimaldi.paracommunity.communities.Community;
import com.ichlebimaldi.paracommunity.communities.Guild;
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
    private ParaCommunity plugin;

    public ClaimEvent(ParaCommunity plugin) {
        this.plugin = plugin;
        data = new SQLGetter(this.plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

//    @EventHandler
//    public void onPlayerClicked(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//        Community community = data.getCommunity(player);
//        Action action = event.getAction();
//        ItemStack item = event.getItem();
//
//        Block block = player.getTargetBlock(null, 100);
//        if(item != null && item.getItemMeta().getDisplayName().equals("§5Claim")){
//            Area area = new Area(plugin, community);
//
//            if (action.equals(Action.LEFT_CLICK_BLOCK)) {
//
//                area.setLoc1(block.getLocation(), player);
//                player.sendBlockChange(data.getFirstLocation(community), data.getFirstLocation(community).getBlock().getBlockData().getMaterial(),(byte) 0);
//
//            } else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
//
//                area.setLoc2(block.getLocation(), player);
//                player.sendBlockChange(data.getSecondLocation(community), data.getSecondLocation(community).getBlock().getBlockData().getMaterial(),(byte) 0);
//
//            }
//
//            event.setCancelled(true);
//        }
//    }

//    @EventHandler
//    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
//        Player player = event.getPlayer();
//        Community community = data.getCommunity(player);
//        Inventory inv = player.getInventory();
//        ItemStack item = inv.getItem(event.getNewSlot());
//        if (item != null && item.getItemMeta().getDisplayName().equals("§5Claim")){
//            player.sendMessage(data.getFirstLocation(community).toString());
//            for(int i = 0; i< 256; i++) {
//                Location loc1 = new Location(data.getWorld(community), data.getFirstLocation(community).getX(),i,data.getFirstLocation(community).getZ());
//                Location loc2 = new Location(data.getWorld(community), data.getSecondLocation(community).getX(),i,data.getSecondLocation(community).getZ());
//                Location test = new Location(data.getWorld(community), data.getFirstLocation(community).getX(), loc1.getWorld().getHighestBlockAt(loc1).getY(), data.getFirstLocation(community).getZ());
//                Location test2 = new Location(data.getWorld(community), data.getSecondLocation(community).getX(), loc1.getWorld().getHighestBlockAt(loc1).getY(), data.getSecondLocation(community).getZ());
//                test.setY(test.getY()+1);
//                test2.setY(test2.getY()+1);
//                player.sendBlockChange(test, Material.RED_STAINED_GLASS,(byte) 14);
//                player.sendBlockChange(test2, Material.RED_STAINED_GLASS,(byte) 14);
//            }
//
//
//        } else {
//            Location loc1 = new Location(data.getWorld(community), data.getFirstLocation(community).getX(),0,data.getFirstLocation(community).getZ());
//            Location loc2 = new Location(data.getWorld(community), data.getSecondLocation(community).getX(),0,data.getSecondLocation(community).getZ());
//
//            Material material1 = loc1.getBlock().getBlockData().getMaterial();
//            Material material2 = loc2.getBlock().getBlockData().getMaterial();
//
//            Location test = new Location(data.getWorld(community), data.getFirstLocation(community).getX(), loc1.getWorld().getHighestBlockAt(loc1).getY(), data.getFirstLocation(community).getZ());
//            Location test2 = new Location(data.getWorld(community), data.getSecondLocation(community).getX(), loc1.getWorld().getHighestBlockAt(loc1).getY(), data.getSecondLocation(community).getZ());
//            test.setY(test.getY()+1);
//            test2.setY(test2.getY()+1);
//
//            player.sendBlockChange(test, data.getFirstLocation(community).getBlock().getBlockData().getMaterial(),(byte) 0);
//            player.sendBlockChange(test2, data.getSecondLocation(community).getBlock().getBlockData().getMaterial(),(byte) 0);
//        }
//
//
//    }
}
