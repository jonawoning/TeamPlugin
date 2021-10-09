package nl.snowpix.kingdom.events;

import me.clip.placeholderapi.PlaceholderAPI;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onChat implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onPlayerChatEvent(AsyncPlayerChatEvent e){
        if (Kingdom.instance.kGetConfig().Use_Chat){
            Player player = e.getPlayer();
            User user = Kingdom.instance.kgetUserManager().getUser(player);

            e.setCancelled(true);

            if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){

                final Character eersteletter = e.getMessage().charAt(0);
                if (eersteletter == Kingdom.instance.kGetConfig().public_letter){
                    if (Kingdom.instance.kGetConfig().Public_Chat){
                        for (Player people : Bukkit.getOnlinePlayers()){
                            people.sendMessage(PlaceholderAPI.setPlaceholders(player, Kingdom.instance.kGetConfig().public_format).replace("%player%", player.getName()).replace("%message%", (player.hasPermission("kingdom.colorchat")) ? ChatColor.translateAlternateColorCodes('&', e.getMessage().substring(1)) : e.getMessage().substring(1)));
                        }
                        return;
                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().This_Channel_Is_Off);
                    }
                }
                if (eersteletter == Kingdom.instance.kGetConfig().roleplay_letter){
                    if (player.hasPermission(Kingdom.instance.kGetConfig().roleplay_channel_perm)){
                        if (Kingdom.instance.kGetConfig().Roleplay_Chat){
                            for (Player people : Bukkit.getOnlinePlayers()){
                                people.sendMessage(PlaceholderAPI.setPlaceholders(player, Kingdom.instance.kGetConfig().roleplay_format).replace("%player%", player.getName()).replace("%message%", (player.hasPermission("kingdom.colorchat")) ? ChatColor.translateAlternateColorCodes('&', e.getMessage().substring(1)) : e.getMessage().substring(1)));
                            }
                            return;
                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().This_Channel_Is_Off);
                        }
                    }
                }
                if (eersteletter == Kingdom.instance.kGetConfig().staff_letter){
                    if (player.hasPermission(Kingdom.instance.kGetConfig().staff_channel_perm)){
                        for (Player people : Bukkit.getOnlinePlayers()){
                            people.sendMessage(PlaceholderAPI.setPlaceholders(player, Kingdom.instance.kGetConfig().staff_format).replace("%player%", player.getName()).replace("%message%", (player.hasPermission("kingdom.colorchat")) ? ChatColor.translateAlternateColorCodes('&', e.getMessage().substring(1)) : e.getMessage().substring(1)));
                        }
                        return;
                    }
                }

                for (Player people : Bukkit.getOnlinePlayers()){
                    User user1 = Kingdom.instance.kgetUserManager().getUser(people);
                    if (user1.getKingdom().equalsIgnoreCase(user.getKingdom())){
                        people.sendMessage(PlaceholderAPI.setPlaceholders(player, Kingdom.instance.kGetConfig().kingdom_format).replace("%player%", player.getName()).replace("%message%", (player.hasPermission("kingdom.colorchat")) ? ChatColor.translateAlternateColorCodes('&', e.getMessage()) : e.getMessage()));
                    }
                }

            }else{

                for (Player people : Bukkit.getOnlinePlayers()){
                    people.sendMessage(PlaceholderAPI.setPlaceholders(player, Kingdom.instance.kGetConfig().public_format).replace("%player%", player.getName()).replace("%message%", (player.hasPermission("kingdom.colorchat")) ? ChatColor.translateAlternateColorCodes('&', e.getMessage()) : e.getMessage()));
                }

            }



        }
    }

}
