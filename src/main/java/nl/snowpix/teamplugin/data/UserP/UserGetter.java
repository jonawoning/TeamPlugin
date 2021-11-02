package nl.snowpix.teamplugin.data.UserP;

import nl.snowpix.teamplugin.TeamPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserGetter {

    public static CompletableFuture<String> getPlayerTeam(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Team"));
            result.complete(blabla);
        });
        return result;
    }

    public static CompletableFuture<String> getPlayerTeam_Prefix(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Team_Prefix"));
            result.complete( blabla);

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerRole(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Role"));
            result.complete(blabla);
        });
        return result;
    }

    public static CompletableFuture<String> getPlayerRole_Prefix(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Role_Prefix"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerRole_Display(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Role_Display"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<Integer> getPlayerRole_Weight(Player player){
        final CompletableFuture<Integer> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final int blabla = cfg_playerdata.getInt("Rank_Weight");
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<List> getPlayerPermissions(Player player){
        final CompletableFuture<List> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final String playerrank = String.valueOf(cfg_playerdata.get("Role"));

            List<String> permissionscfg = TeamPlugin.instance.kgetRoles().cfg_ranksfile.getStringList("Roles." + playerrank + ".Permissions");

            result.complete(permissionscfg);

        });
        return result;
    }

}
