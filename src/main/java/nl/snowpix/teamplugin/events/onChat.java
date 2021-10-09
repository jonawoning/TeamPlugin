package nl.snowpix.teamplugin.events;

import me.clip.placeholderapi.PlaceholderAPI;
import nl.snowpix.teamplugin.TeamPlugin;
import nl.snowpix.teamplugin.data.UserP.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onChat implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e){
        if (TeamPlugin.instance.kgetConfig().Use_Chat_System){
            Player player = e.getPlayer();
            User user = TeamPlugin.instance.kgetUserManager().getUser(player);

            e.setCancelled(true);

            final Character eersteletter = e.getMessage().charAt(0);

            if (eersteletter == TeamPlugin.instance.kgetConfig().Global_Letter){
                for (Player people : Bukkit.getOnlinePlayers()){
                    people.sendMessage(PlaceholderAPI.setPlaceholders(player, TeamPlugin.instance.kgetConfig().Global_Chat_Format).replace("%player%", player.getName())
                    .replace("%message%", (player.hasPermission("teams.colorchat"))
                            ? ChatColor.translateAlternateColorCodes('&', e.getMessage().substring(1)) : e.getMessage().substring(1)));
                }
                return;
            }
            if (user.getTeam() != null && !user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name)) {
                for (Player people : Bukkit.getOnlinePlayers()) {
                    User user2 = TeamPlugin.instance.kgetUserManager().getUser(people);
                    if (user2.getTeam().equalsIgnoreCase(user.getTeam())) {
                        people.sendMessage(PlaceholderAPI.setPlaceholders(player, TeamPlugin.instance.kgetConfig().Team_Chat_Format).replace("%player%", player.getName())
                                .replace("%message%", (player.hasPermission("teams.colorchat"))
                                        ? ChatColor.translateAlternateColorCodes('&', e.getMessage()) : e.getMessage()));
                    }
                }
                return;
            }else{
                for (Player people : Bukkit.getOnlinePlayers()){
                    people.sendMessage(PlaceholderAPI.setPlaceholders(player, TeamPlugin.instance.kgetConfig().Global_Chat_Format).replace("%player%", player.getName())
                            .replace("%message%", (player.hasPermission("teams.colorchat"))
                                    ? ChatColor.translateAlternateColorCodes('&', e.getMessage()) : e.getMessage()));
                }
            }

        }
    }

}
