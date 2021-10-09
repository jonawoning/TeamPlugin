package nl.snowpix.kingdom.events;

import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class onRespawn implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onPlayerRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        if (Kingdom.instance.kGetConfig().SpawnOnDeath){
            User user = Kingdom.instance.kgetUserManager().getUser(player);

            if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
                Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(user.getKingdom().toLowerCase());
                if (kingdoms.getSpawn() != null){
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(kingdoms.getSpawn());
                        }
                    }.runTaskLater(Kingdom.instance, 15);
                }
            }
        }
    }
}
