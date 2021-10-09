package nl.snowpix.kingdom.events;

import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.tab.NametagManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onLeave implements Listener {

    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();

        Kingdom.instance.kGetConfig().staffmode.remove(player);

        Kingdom.instance.kgetUserManager().removeUser(player);

        NametagManager.getInstance().reset(player.getName());

    }

}
