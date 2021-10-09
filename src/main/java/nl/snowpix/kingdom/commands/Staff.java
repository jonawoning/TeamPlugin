package nl.snowpix.kingdom.commands;

import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Staff implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("You have to be a player to execute this command.");
            return true;
        }
        Player player = (Player) sender;
        if (player.hasPermission(Kingdom.instance.kGetConfig().Staff_Perm)){
            if (args.length > 0){
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target.hasPermission(Kingdom.instance.kGetConfig().Staff_Perm)){
                    if (target != null){
                        if (!Kingdom.instance.kGetConfig().staffmode.contains(target)){
                            Kingdom.instance.kGetConfig().staffmode.add(target);
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Yes_Set_Other_Vanish.replace("%target%", target.getName()));
                            target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Yes_To_User.replace("%target%", player.getName()));
                            target.sendTitle(Kingdom.instance.kgetMessages().Vanish_Title, Kingdom.instance.kgetMessages().Vanish_Yes_Subtitle, 20, 35,20);
                            for (Player people : Bukkit.getOnlinePlayers()) {
                                if(!people.hasPermission(Kingdom.instance.kGetConfig().Staff_Perm))
                                    people.hidePlayer(Kingdom.instance, target);
                            }
                            target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Other_Vanish);
                            for(Player p : Kingdom.instance.kGetConfig().staffmode) {
                                target.sendMessage("- " + ChatColor.GRAY + p.getName());
                            }
                            target.setAllowFlight(true);
                            target.setFlying(true);
                            target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 2));
                        }else{
                            Kingdom.instance.kGetConfig().staffmode.remove(target);
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Set_Other_Vanish.replace("%target%", target.getName()));
                            target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_To_User.replace("%target%", player.getName()));
                            target.sendTitle(Kingdom.instance.kgetMessages().Vanish_Title, Kingdom.instance.kgetMessages().Vanish_No_Subtitle, 20, 35,20);
                            for (Player people : Bukkit.getOnlinePlayers()) {
                                people.showPlayer(Kingdom.instance, target);
                            }
                            target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Other_Vanish);
                            for(Player p : Kingdom.instance.kGetConfig().staffmode) {
                                target.sendMessage("- " + ChatColor.GRAY + p.getName());
                            }
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            target.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Incorrect_Player);
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + "§cUse: /staff (Player)");
                    }
                }else{
                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Not_Perm.replace("%permission%", Kingdom.instance.kGetConfig().Staff_Perm));
                }
            }else{
                if (!Kingdom.instance.kGetConfig().staffmode.contains(player)){
                    Kingdom.instance.kGetConfig().staffmode.add(player);
                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Yes_Vanish);
                    player.sendTitle(Kingdom.instance.kgetMessages().Vanish_Title, Kingdom.instance.kgetMessages().Vanish_Yes_Subtitle, 20, 35,20);
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        if(!people.hasPermission(Kingdom.instance.kGetConfig().Staff_Perm))
                            people.hidePlayer(Kingdom.instance, player);
                    }
                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Other_Vanish);
                    for(Player p : Kingdom.instance.kGetConfig().staffmode) {
                        player.sendMessage("- " + ChatColor.GRAY + p.getName());
                    }
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 2));
                }else{
                    Kingdom.instance.kGetConfig().staffmode.remove(player);
                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Vanish);
                    player.sendTitle(Kingdom.instance.kgetMessages().Vanish_Title, Kingdom.instance.kgetMessages().Vanish_No_Subtitle, 20, 35,20);
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        people.showPlayer(Kingdom.instance, player);
                    }
                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Other_Vanish);
                    for(Player p : Kingdom.instance.kGetConfig().staffmode) {
                        player.sendMessage("- " + ChatColor.GRAY + p.getName());
                    }
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                }
            }
        }else{
            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
        }
        return false;
    }
}
