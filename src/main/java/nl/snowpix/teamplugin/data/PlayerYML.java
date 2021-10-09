package nl.snowpix.teamplugin.data;

import nl.snowpix.teamplugin.TeamPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerYML {

    public static void CheckPlayer(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {

            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final File playerdata = new File("plugins//TeamPlugin//data//playerdata//" + player.getUniqueId() + ".yml");

            final String playerteam = String.valueOf(cfg_playerdata.get("Team"));

            if (!playerdata.exists()){
                //PLAYERDATA CREATE

                cfg_playerdata.set("Team", TeamPlugin.instance.kgetConfig().NoTeam_Name);
                cfg_playerdata.set("Team_Prefix", TeamPlugin.instance.kgetConfig().NoTeam_Prefix);
                cfg_playerdata.set("Role", TeamPlugin.instance.kgetConfig().NoTeam_Role);
                cfg_playerdata.set("Role_Prefix", TeamPlugin.instance.kgetConfig().NoTeam_Role_Prefix);

                try {
                    cfg_playerdata.save(playerdata);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if (!TeamPlugin.instance.kgetSystemMethods().CheckTeam(playerteam.toLowerCase())) {

                //CHECKS IF THE TEAM IS STILL THERE
                cfg_playerdata.set("Team", TeamPlugin.instance.kgetConfig().NoTeam_Name);
                cfg_playerdata.set("Team_Prefix", TeamPlugin.instance.kgetConfig().NoTeam_Prefix);
                cfg_playerdata.set("Role", TeamPlugin.instance.kgetConfig().NoTeam_Role);
                cfg_playerdata.set("Role_Prefix", TeamPlugin.instance.kgetConfig().NoTeam_Role_Prefix);

                try {
                    cfg_playerdata.save(playerdata);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TeamPlugin.instance.kgetUserManager().removeUser(player);
                TeamPlugin.instance.kgetUserManager().addUser(player);
            }else{

                //NORMAL LOADED, CHECKS PREFIXES ETC.
                final YamlConfiguration cfg_teamdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams", playerteam.toLowerCase() + ".yml"));
                final YamlConfiguration cfg_ranksfile = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin", "roles.yml"));

                cfg_playerdata.set("Team", cfg_teamdata.get("Team_Name"));
                cfg_playerdata.set("Team_Prefix", cfg_teamdata.get("Team_Prefix"));
                cfg_playerdata.set("Role", );
                cfg_playerdata.set("Role_Prefix", TeamPlugin.instance.kgetConfig().NoTeam_Role_Prefix);

                try {
                    cfg_playerdata.save(playerdata);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

}
