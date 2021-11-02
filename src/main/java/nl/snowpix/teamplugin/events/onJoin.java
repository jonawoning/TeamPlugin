package nl.snowpix.teamplugin.events;

import nl.snowpix.teamplugin.TeamPlugin;
import nl.snowpix.teamplugin.data.PlayerYML;
import nl.snowpix.teamplugin.data.UserP.User;
import nl.snowpix.teamplugin.nametag.NametagManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class onJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        TeamPlugin.instance.kgetUserManager().addUser(player);

        PlayerYML.CheckPlayer(player);

        new BukkitRunnable() {
            @Override
            public void run() {



                TeamPlugin.instance.kgetUserManager().removeUser(player);
                TeamPlugin.instance.kgetUserManager().addUser(player);

                if (TeamPlugin.instance.kgetConfig().Use_Tab) {
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        User user1 = TeamPlugin.instance.kgetUserManager().getUser(player1);
                        NametagManager.getInstance().setNametag(player1.getName(), (user1.getTeam_prefix() != null && !user1.getTeam_prefix().equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', user1.getTeam_prefix()) : ""), "");
                    }
                }


            }
        }.runTaskLater(TeamPlugin.instance, 25);
    }


}
