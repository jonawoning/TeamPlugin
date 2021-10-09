package nl.snowpix.kingdom.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TLCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            User user = Kingdom.instance.kgetUserManager().getUser(player);
            if (user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                return true;
            }

            final int x = player.getLocation().getBlock().getX();
            final int y = player.getLocation().getBlock().getY();
            final int z = player.getLocation().getBlock().getZ();
            final String locationstring = "§7X: §f" + x + " §7Y: §f" + y + " §7Z: §f" + z;

            for (Player looped : Bukkit.getOnlinePlayers()){
                User user1 = Kingdom.instance.kgetUserManager().getUser(looped);
                if (user1.getKingdom().equalsIgnoreCase(user.getKingdom())){
                    looped.sendMessage(PlaceholderAPI.setPlaceholders(player, Kingdom.instance.kGetConfig().kingdom_format).replace("%player%", player.getName()).replace("%message%", locationstring));
                }
            }
        }
        return false;
    }
}
