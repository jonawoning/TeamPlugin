package nl.snowpix.kingdom.events;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.methods.ExtraMethods;
import nl.snowpix.kingdom.system.Mines;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockBreakMine implements Listener {

    public static Map<Location, Material> blocksMap = new HashMap<>();
    public static List<String> configRegions;

    public static int coal_ore;
    public static int iron_ore;
    public static int gold_ore;
    public static int lapis_ore;
    public static int redstone_ore;
    public static int diamond_ore;
    public static int emerald_ore;

    @EventHandler
    public void onMineBreak(BlockBreakEvent event){
        if (Kingdom.instance.kGetConfig().Use_Mineisland){
            if (configRegions != null && configRegions.size() > 0 && event.getPlayer().getGameMode() != GameMode.CREATIVE){
                ApplicableRegionSet regions = ExtraMethods.getRegions(event.getBlock().getLocation());
                if (regions.size() > 0){
                    for (ProtectedRegion region : regions){
                        if (configRegions.contains(region.getId())){
                            if (event.getBlock().getType() == Material.COAL_ORE){
                                event.setCancelled(true);
                                blocksMap.put(event.getBlock().getLocation(), event.getBlock().getType());
                                event.getBlock().setType(Material.BEDROCK);
                                Integer oreRespawn = coal_ore;
                                Location location = event.getBlock().getLocation();
                                BukkitTask respawn = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!location.getWorld().isChunkLoaded(location.getChunk())){
                                            location.getChunk().load(true);
                                        }
                                        location.getBlock().setType(Material.COAL_ORE);
                                        blocksMap.remove(location);
                                    }
                                }.runTaskLater(Kingdom.instance, 20*oreRespawn);
                                event.getPlayer().getInventory().addItem(new ItemStack(Material.COAL_ORE));
                            }else if (event.getBlock().getType() == Material.DIAMOND_ORE){
                                event.setCancelled(true);
                                blocksMap.put(event.getBlock().getLocation(), event.getBlock().getType());
                                event.getBlock().setType(Material.BEDROCK);
                                Integer oreRespawn = diamond_ore;
                                Location location = event.getBlock().getLocation();
                                BukkitTask respawn = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!location.getWorld().isChunkLoaded(location.getChunk())){
                                            location.getChunk().load(true);
                                        }
                                        location.getBlock().setType(Material.DIAMOND_ORE);
                                        blocksMap.remove(location);
                                    }
                                }.runTaskLater(Kingdom.instance, 20*oreRespawn);
                                event.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND_ORE));
                            }else if (event.getBlock().getType() == Material.EMERALD_ORE){
                                event.setCancelled(true);
                                blocksMap.put(event.getBlock().getLocation(), event.getBlock().getType());
                                event.getBlock().setType(Material.BEDROCK);
                                Integer oreRespawn = emerald_ore;
                                Location location = event.getBlock().getLocation();
                                BukkitTask respawn = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!location.getWorld().isChunkLoaded(location.getChunk())){
                                            location.getChunk().load(true);
                                        }
                                        location.getBlock().setType(Material.EMERALD_ORE);
                                        blocksMap.remove(location);
                                    }
                                }.runTaskLater(Kingdom.instance, 20*oreRespawn);
                                event.getPlayer().getInventory().addItem(new ItemStack(Material.EMERALD_ORE));
                            }else if (event.getBlock().getType() == Material.REDSTONE_ORE){
                                event.setCancelled(true);
                                blocksMap.put(event.getBlock().getLocation(), event.getBlock().getType());
                                event.getBlock().setType(Material.BEDROCK);
                                Integer oreRespawn = redstone_ore;
                                Location location = event.getBlock().getLocation();
                                BukkitTask respawn = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!location.getWorld().isChunkLoaded(location.getChunk())){
                                            location.getChunk().load(true);
                                        }
                                        location.getBlock().setType(Material.REDSTONE_ORE);
                                        blocksMap.remove(location);
                                    }
                                }.runTaskLater(Kingdom.instance, 20*oreRespawn);
                                event.getPlayer().getInventory().addItem(new ItemStack(Material.REDSTONE_ORE));
                            }else if (event.getBlock().getType() == Material.GOLD_ORE){
                                event.setCancelled(true);
                                blocksMap.put(event.getBlock().getLocation(), event.getBlock().getType());
                                event.getBlock().setType(Material.BEDROCK);
                                Integer oreRespawn = gold_ore;
                                Location location = event.getBlock().getLocation();
                                BukkitTask respawn = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!location.getWorld().isChunkLoaded(location.getChunk())){
                                            location.getChunk().load(true);
                                        }
                                        location.getBlock().setType(Material.GOLD_ORE);
                                        blocksMap.remove(location);
                                    }
                                }.runTaskLater(Kingdom.instance, 20*oreRespawn);
                                event.getPlayer().getInventory().addItem(new ItemStack(Material.GOLD_ORE));
                            }else if (event.getBlock().getType() == Material.LAPIS_ORE){
                                event.setCancelled(true);
                                blocksMap.put(event.getBlock().getLocation(), event.getBlock().getType());
                                event.getBlock().setType(Material.BEDROCK);
                                Integer oreRespawn = lapis_ore;
                                Location location = event.getBlock().getLocation();
                                BukkitTask respawn = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!location.getWorld().isChunkLoaded(location.getChunk())){
                                            location.getChunk().load(true);
                                        }
                                        location.getBlock().setType(Material.LAPIS_ORE);
                                        blocksMap.remove(location);
                                    }
                                }.runTaskLater(Kingdom.instance, 20*oreRespawn);
                                event.getPlayer().getInventory().addItem(new ItemStack(Material.LAPIS_ORE));
                            }else if (event.getBlock().getType() == Material.IRON_ORE){
                                event.setCancelled(true);
                                blocksMap.put(event.getBlock().getLocation(), event.getBlock().getType());
                                event.getBlock().setType(Material.BEDROCK);
                                Integer oreRespawn = iron_ore;
                                Location location = event.getBlock().getLocation();
                                BukkitTask respawn = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!location.getWorld().isChunkLoaded(location.getChunk())){
                                            location.getChunk().load(true);
                                        }
                                        location.getBlock().setType(Material.IRON_ORE);
                                        blocksMap.remove(location);
                                    }
                                }.runTaskLater(Kingdom.instance, 20*oreRespawn);
                                event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_ORE));
                            }else {
                                if(Kingdom.instance.getConfig().getBoolean("Block_unwanted_break")){
                                    if (!event.getPlayer().hasPermission("mineisland.bypass")) {
                                        event.setCancelled(true);
                                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Kingdom.instance.getConfig().getString("Block_unwanted_break_message")));
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public static void ReloadMineDataYML(){
        if (Mines.mines.exists()){
            BlockBreakMine.configRegions = Mines.cfg_mines.getStringList("RegionMines");
            BlockBreakMine.coal_ore = Kingdom.instance.c.getInt("Respawn.coal_ore");
            BlockBreakMine.iron_ore = Kingdom.instance.c.getInt("Respawn.iron_ore");
            BlockBreakMine.gold_ore = Kingdom.instance.c.getInt("Respawn.gold_ore");
            BlockBreakMine.lapis_ore = Kingdom.instance.c.getInt("Respawn.lapis_ore");
            BlockBreakMine.redstone_ore = Kingdom.instance.c.getInt("Respawn.redstone_ore");
            BlockBreakMine.diamond_ore = Kingdom.instance.c.getInt("Respawn.diamond_ore");
            BlockBreakMine.emerald_ore = Kingdom.instance.c.getInt("Respawn.emerald_ore");
        }else{
            Mines.cfg_mines.set("RegionMines", BlockBreakMine.configRegions);
            try {
                Mines.cfg_mines.save(Mines.mines);
            } catch (IOException e){
                e.printStackTrace();
            }

            BlockBreakMine.configRegions = Mines.cfg_mines.getStringList("RegionMines");
            BlockBreakMine.coal_ore = Kingdom.instance.c.getInt("Respawn.coal_ore");
            BlockBreakMine.iron_ore = Kingdom.instance.c.getInt("Respawn.iron_ore");
            BlockBreakMine.gold_ore = Kingdom.instance.c.getInt("Respawn.gold_ore");
            BlockBreakMine.lapis_ore = Kingdom.instance.c.getInt("Respawn.lapis_ore");
            BlockBreakMine.redstone_ore = Kingdom.instance.c.getInt("Respawn.redstone_ore");
            BlockBreakMine.diamond_ore = Kingdom.instance.c.getInt("Respawn.diamond_ore");
            BlockBreakMine.emerald_ore = Kingdom.instance.c.getInt("Respawn.emerald_ore");
        }
    }

}
