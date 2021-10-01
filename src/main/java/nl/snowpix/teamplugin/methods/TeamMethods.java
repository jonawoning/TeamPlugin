package nl.snowpix.teamplugin.methods;

import nl.snowpix.teamplugin.TeamPlugin;
import nl.snowpix.teamplugin.data.TeamP.Team;
import nl.snowpix.teamplugin.data.UserP.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class TeamMethods {

    public static void CreateTeam(String teamName, String teamPrefix){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {

            final YamlConfiguration cfg_teamdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//Data//Teams//", teamName.toLowerCase() + ".yml"));
            final File teamfile = new File("plugins//TeamPlugin//Data//Teams//" + teamName.toLowerCase() + ".yml");

            cfg_teamdata.set("Team_Name", teamName);
            cfg_teamdata.set("Team_Prefix", teamPrefix);

            try {
                cfg_teamdata.save(teamfile);
            } catch (IOException e){
                e.printStackTrace();
            }

            TeamPlugin.instance.kgetTeamManager().removeTeam(teamName.toLowerCase());
            TeamPlugin.instance.kgetTeamManager().addTeam(teamName.toLowerCase());
            TeamPlugin.instance.kgetSystemMethods().Teams.add(teamName.toLowerCase());

        });
    }

    public static void DeleteTeam(String teamName){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {

            final File teamfile = new File("plugins//TeamPlugin//Data//Teams//" + teamName.toLowerCase() + ".yml");

            teamfile.delete();

            TeamPlugin.instance.kgetTeamManager().removeTeam(teamName.toLowerCase());
            TeamPlugin.instance.kgetSystemMethods().Teams.remove(teamName.toLowerCase());

        });
    }

    public static void SetPlayerTeam(Player player, String teamName){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {

            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final File playerdata = new File("plugins//TeamPlugin//data//playerdata//" + player.getUniqueId() + ".yml");

            Team team = TeamPlugin.instance.kgetTeamManager().getTeam(teamName.toLowerCase());

            cfg_playerdata.set("Team", team.getOfficialName());
            cfg_playerdata.set("Team_Prefix", team.getTeamPrefix());

            try {
                cfg_playerdata.save(playerdata);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TeamPlugin.instance.kgetUserManager().removeUser(player);
            TeamPlugin.instance.kgetUserManager().addUser(player);

            User user = TeamPlugin.instance.kgetUserManager().getUser(player);

            user.setTeam_Name(team.getOfficialName());
            user.setTeam_Prefix(team.getTeamPrefix());

        });
    }

    public static void NoTeamSetPlayer(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {

            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
            final File playerdata = new File("plugins//TeamPlugin//data//playerdata//" + player.getUniqueId() + ".yml");

            cfg_playerdata.set("Team", TeamPlugin.instance.kgetConfig().NoTeam_Name);
            cfg_playerdata.set("Team_Prefix", TeamPlugin.instance.kgetConfig().NoTeam_Prefix);

            try {
                cfg_playerdata.save(playerdata);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TeamPlugin.instance.kgetUserManager().removeUser(player);
            TeamPlugin.instance.kgetUserManager().addUser(player);

            User user = TeamPlugin.instance.kgetUserManager().getUser(player);

            user.setTeam_Name(TeamPlugin.instance.kgetConfig().NoTeam_Name);
            user.setTeam_Prefix(TeamPlugin.instance.kgetConfig().NoTeam_Prefix);

        });
    }

}
