package nl.snowpix.kingdom.commands;

import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.events.BlockBreakMine;
import nl.snowpix.kingdom.system.Mines;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.List;

public class MineIsland implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("mineisland.use")){
            if (args.length > 0){
                if (args[0].equalsIgnoreCase("define")){
                    if (args.length > 1){
                        String regionname = args[1].toLowerCase();
                        if (!Mines.cfg_mines.getStringList("RegionMines").contains(regionname)){
                            List<String> list = Mines.cfg_mines.getStringList("RegionMines");
                            list.add(args[1].toLowerCase());
                            BlockBreakMine.configRegions = list;
                            Mines.cfg_mines.set("RegionMines", list);
                            try {
                                Mines.cfg_mines.save(Mines.mines);
                            } catch (IOException e){
                                e.printStackTrace();
                            }

                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().MineIsland_Added.replace("%region%", regionname));
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().MineIsland_AlreadyMine);
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Use: /mineisland define (region)");
                    }
                }else if (args[0].equalsIgnoreCase("remove")){
                    if (args.length > 1){
                        String regionname = args[1].toLowerCase();
                        if (Mines.cfg_mines.getStringList("RegionMines").contains(regionname)){
                            List<String> list = Mines.cfg_mines.getStringList("RegionMines");
                            list.remove(args[1].toLowerCase());
                            BlockBreakMine.configRegions = list;
                            Mines.cfg_mines.set("RegionMines", list);
                            try {
                                Mines.cfg_mines.save(Mines.mines);
                            } catch (IOException e){
                                e.printStackTrace();
                            }

                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().MineIsland_Removed.replace("%region%", regionname));
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().MineIsland_NotAMine);
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Use: /mineisland remove (region)");
                    }
                }else if (args[0].equalsIgnoreCase("reload")){
                    if (sender.hasPermission("mineisland.reload")){
                        BlockBreakMine.ReloadMineDataYML();
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().MineIsland_Reloaded);
                    }


                }else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &5&lMineIsland &8&l&m-----" + "\n"
                            + "&d" + "\n"
                            + "&d/mineisland define &7(region) &7- Define a region as an MineIsland. (mineisland.define)" + "\n"
                            + "&d/mineisland remove &7(region) &7- Undefine a region as an MineIsland. (mineisland.remove)" + "\n"
                            + "&d/mineisland reload &7- Reload the whole MineIsland config data." + "\n"
                            + "\n"
                            + "&7The permission &amineisland.use &7is needed to use the commands."
                            + "&e"));
                }
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &5&lMineIsland &8&l&m-----" + "\n"
                        + "&d" + "\n"
                        + "&d/mineisland define &7(region) &7- Define a region as an MineIsland. (mineisland.define)" + "\n"
                        + "&d/mineisland remove &7(region) &7- Undefine a region as an MineIsland. (mineisland.remove)" + "\n"
                        + "&d/mineisland reload &7- Reload the whole MineIsland config data." + "\n"
                        + "\n"
                        + "&7The permission &amineisland.use &7is needed to use the commands."
                        + "&e"));
            }
        }

        return false;
    }
}
