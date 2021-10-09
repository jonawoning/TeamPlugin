package nl.snowpix.kingdom.events;

import nl.snowpix.kingdom.Kingdom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onMove implements Listener {

    @EventHandler
    public void PlayerMove(PlayerMoveEvent event){
        if (event.getTo().getBlockX() == event.getFrom().getBlockX() && event.getTo().getBlockY() == event.getFrom().getBlockY() && event.getTo().getBlockZ() == event.getFrom().getBlockZ()) return;
        if (Kingdom.instance.kGetConfig().hspawner.containsKey(event.getPlayer())){
            event.getPlayer().sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kGetConfig().SpawnTimer_Move);
            Kingdom.instance.kGetConfig().hspawner.get(event.getPlayer()).cancel();
            Kingdom.instance.kGetConfig().hspawner.remove(event.getPlayer());
        }
    }

}
