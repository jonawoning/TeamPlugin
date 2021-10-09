package nl.snowpix.kingdom.events;

import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.tab.NametagManager;
import nl.snowpix.kingdom.api.ScoreboardB;
import nl.snowpix.kingdom.methods.ExtraMethods;
import nl.snowpix.kingdom.methods.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class onJoin implements Listener {

    @EventHandler
    private void OnPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        Kingdom.instance.kgetUserManager().addUser(player);

        PlayerData.CheckPlayerData(player);

        new BukkitRunnable() {
            @Override
            public void run() {

                Kingdom.instance.kgetUserManager().removeUser(player);
                Kingdom.instance.kgetUserManager().addUser(player);

                ExtraMethods.SendJoinMessage(player);

                User user1 = Kingdom.instance.kgetUserManager().getUser(player);
                NametagManager.getInstance().setNametag(player.getName(), (user1.getTab() != null && !user1.getTab().equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', user1.getTab()) : ""), (user1.getTabSuffix() != null && !user1.getTabSuffix().equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', user1.getTabSuffix()) : null));

            }
        }.runTaskLaterAsynchronously(Kingdom.instance, 25);

        //VANISH CHECK
        if (!player.hasPermission(Kingdom.instance.kGetConfig().Staff_Perm)) {
            for (int i = 0; i < Kingdom.instance.kGetConfig().staffmode.size(); i++) {
                player.hidePlayer(Kingdom.instance, Kingdom.instance.kGetConfig().staffmode.get(i));
            }
        }

        ScoreboardB.setScoreBoard(player);

    }




}
