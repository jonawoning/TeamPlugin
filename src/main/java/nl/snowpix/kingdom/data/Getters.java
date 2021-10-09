package nl.snowpix.kingdom.data;

import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Getters {

    public static CompletableFuture<String> getPlayerKD(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Kingdom"));
            result.complete(blabla);
        });
        return result;
    }

    public static CompletableFuture<String> getPlayerKD_Prefix(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Kingdom_Prefix"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerKD_Display(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Kingdom_Display"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerRank(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Rank"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<Integer> getPlayerRankWeight(Player player){
        final CompletableFuture<Integer> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final int blabla = cfg_playerdata.getInt("Rank_Weight");
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerRankPrefix(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Rank_Prefix"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerRankDisplay(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Rank_Display"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerTab(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Tab"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerTabSuffix(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Rank"));
            if (Kingdom.instance.kgetRanks().cfg_ranksfile.isSet("Ranks." + blabla + ".TabSuffix")){
                final String d = String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + blabla + ".TabSuffix"));
                result.complete(ChatColor.translateAlternateColorCodes('&', d));
            }else{
                result.complete(null);
            }

        });
        return result;
    }

    public static CompletableFuture<String> getPlayerSoort(Player player){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String blabla = String.valueOf(cfg_playerdata.get("Soort"));
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<List> getPlayerPermissions(Player player){
        final CompletableFuture<List> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String playerrank = String.valueOf(cfg_playerdata.get("Rank"));

            List<String> permissionscfg = Kingdom.instance.kgetRanks().cfg_ranksfile.getStringList("Ranks." + playerrank + ".Permissions");

            result.complete(permissionscfg);

        });
        return result;
    }

    public static CompletableFuture<Integer> getPlayerPoints(Player player){
        final CompletableFuture<Integer> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final int blabla = cfg_playerdata.getInt("Points");
            result.complete(blabla);

        });
        return result;
    }

}
