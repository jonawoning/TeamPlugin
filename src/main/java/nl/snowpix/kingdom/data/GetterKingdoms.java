package nl.snowpix.kingdom.data;

import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GetterKingdoms {

    public static CompletableFuture<String> getKingdomName(String kingdom){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final String blabla = String.valueOf(cfg_kingdomdata.get("Naam"));
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<String> getKingdomPrefix(String kingdom){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final String blabla = String.valueOf(cfg_kingdomdata.get("Prefix"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getKingdomDisplay(String kingdom){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final String blabla = String.valueOf(cfg_kingdomdata.get("Display"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getKingdomTab(String kingdom){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final String blabla = String.valueOf(cfg_kingdomdata.get("Tab"));
            result.complete(ChatColor.translateAlternateColorCodes('&', blabla));

        });
        return result;
    }

    public static CompletableFuture<String> getKingdomSoort(String kingdom){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final String blabla = String.valueOf(cfg_kingdomdata.get("Soort"));
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<Location> getKingdomSpawn(String kingdom){
        final CompletableFuture<Location> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            if (cfg_kingdomdata.isSet("Spawns.World")){
                Location spawn = new Location(Bukkit.getWorld(cfg_kingdomdata.getString("Spawns.World")), cfg_kingdomdata.getDouble("Spawns.X"),
                        cfg_kingdomdata.getDouble("Spawns.Y"),
                        cfg_kingdomdata.getDouble("Spawns.Z"),
                        (float)cfg_kingdomdata.getDouble("Spawns.Yaw"),
                        (float)cfg_kingdomdata.getDouble("Spawns.Pitch"));
                result.complete(spawn);
            }else{
                result.complete(null);
            }

        });
        return result;
    }

    public static CompletableFuture<List> getKingdomRegions(String kingdom){
        final CompletableFuture<List> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final List<String> blabla = cfg_kingdomdata.getStringList("Regions");
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<List> getKingdomAllies(String kingdom){
        final CompletableFuture<List> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final List<String> blabla = cfg_kingdomdata.getStringList("Allies");
            if (blabla != null){
                result.complete(blabla);
            }else{
                result.complete(Collections.singletonList("-"));
            }

        });
        return result;
    }

    public static CompletableFuture<Integer> getKingdomKills(String kingdom){
        final CompletableFuture<Integer> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final int blabla = (int) cfg_kingdomdata.get("Kills");
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<Integer> getKingdomKills_W(String kingdom){
        final CompletableFuture<Integer> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom.toLowerCase() + ".yml"));
            final int blabla = (int) cfg_kingdomdata.get("W_Kills");
            result.complete(blabla);

        });
        return result;
    }

}
