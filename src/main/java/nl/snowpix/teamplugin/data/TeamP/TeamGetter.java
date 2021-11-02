package nl.snowpix.teamplugin.data.TeamP;

import nl.snowpix.teamplugin.TeamPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TeamGetter {

    public static CompletableFuture<String> getTeamName(String teamName){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_teamdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams//", teamName.toLowerCase() + ".yml"));
            final String blabla = String.valueOf(cfg_teamdata.get("Team_Name"));
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<String> getTeamPrefix(String teamName){
        final CompletableFuture<String> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_teamdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams//", teamName.toLowerCase() + ".yml"));
            final String blabla = String.valueOf(cfg_teamdata.get("Team_Prefix"));
            result.complete(blabla);

        });
        return result;
    }

    public static CompletableFuture<List> getTeamAllies(String teamName){
        final CompletableFuture<List> result = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            final YamlConfiguration cfg_teamdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams//", teamName.toLowerCase() + ".yml"));
            final List<String> blabla = cfg_teamdata.getStringList("Allies");
            if (blabla != null){
                result.complete(blabla);
            }else{
                result.complete(Collections.singletonList("-"));
            }

        });
        return result;
    }

}
