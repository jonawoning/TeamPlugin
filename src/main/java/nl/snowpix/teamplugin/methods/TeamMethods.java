package nl.snowpix.teamplugin.methods;

import nl.snowpix.teamplugin.TeamPlugin;
import nl.snowpix.teamplugin.data.TeamP.Team;
import nl.snowpix.teamplugin.data.UserP.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamMethods {

    public static void CreateTeam(String teamName, String teamPrefix){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {

            final YamlConfiguration cfg_teamdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams//", teamName.toLowerCase() + ".yml"));
            final File teamfile = new File("plugins//TeamPlugin//data//teams//" + teamName.toLowerCase() + ".yml");

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

            final File teamfile = new File("plugins//TeamPlugin//data//teams//" + teamName.toLowerCase() + ".yml");

            teamfile.delete();

            TeamPlugin.instance.kgetTeamManager().removeTeam(teamName.toLowerCase());
            TeamPlugin.instance.kgetSystemMethods().Teams.remove(teamName.toLowerCase());

        });
    }

    public static void AddAnAlly(String Team1Arg, String Team2Arg){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            Team team1 = TeamPlugin.instance.kgetTeamManager().getTeam(Team1Arg.toLowerCase());
            Team team2 = TeamPlugin.instance.kgetTeamManager().getTeam(Team2Arg.toLowerCase());

            final YamlConfiguration cfg_teamdata1 = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams//", Team1Arg.toLowerCase() + ".yml"));
            final File teamfile1 = new File("plugins//TeamPlugin//data//teams//" + Team1Arg.toLowerCase() + ".yml");

            final YamlConfiguration cfg_teamdata2 = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams//", Team2Arg.toLowerCase() + ".yml"));
            final File teamfile2 = new File("plugins//TeamPlugin//data//teams//" + Team2Arg.toLowerCase() + ".yml");

            List<String> allies1 = new ArrayList<>();
            allies1 = team1.getAllies();
            allies1.add(Team2Arg.toLowerCase());

            List<String> allies2 = new ArrayList<>();
            allies2 = team2.getAllies();
            allies2.add(Team1Arg.toLowerCase());

            cfg_teamdata1.set("Allies", allies1);
            cfg_teamdata2.set("Allies", allies2);

            try {
                cfg_teamdata1.save(teamfile1);
            } catch (IOException e){
                e.printStackTrace();
            }

            try {
                cfg_teamdata2.save(teamfile2);
            } catch (IOException e){
                e.printStackTrace();
            }

            team1.addAlly(Team2Arg.toLowerCase());

            team2.addAlly(Team1Arg.toLowerCase());

            //CASTED ALLY
            for (Player players : Bukkit.getOnlinePlayers()){
                User u_players = TeamPlugin.instance.kgetUserManager().getUser(players);
                if (u_players.getTeam().equalsIgnoreCase(Team1Arg)){
                    final String prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.kgetConfig().Prefix);
                    players.sendMessage(prefix + TeamPlugin.instance.kgetMessages().Casted_Ally_Message
                    .replace("%team%", team2.getOfficialName()));
                }
            }

            //CASTED ALLY
            for (Player players : Bukkit.getOnlinePlayers()){
                User u_players = TeamPlugin.instance.kgetUserManager().getUser(players);
                if (u_players.getTeam().equalsIgnoreCase(Team2Arg)){
                    final String prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.kgetConfig().Prefix);
                    players.sendMessage(prefix + TeamPlugin.instance.kgetMessages().Casted_Ally_Message
                            .replace("%team%", team1.getOfficialName()));
                }
            }


        });
    }

    public static void RemoveAnAlly(String Team1Arg, String Team2Arg){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            Team team1 = TeamPlugin.instance.kgetTeamManager().getTeam(Team1Arg.toLowerCase());
            Team team2 = TeamPlugin.instance.kgetTeamManager().getTeam(Team2Arg.toLowerCase());

            final YamlConfiguration cfg_teamdata1 = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams//", Team1Arg.toLowerCase() + ".yml"));
            final File teamfile1 = new File("plugins//TeamPlugin//data//teams//" + Team1Arg.toLowerCase() + ".yml");

            final YamlConfiguration cfg_teamdata2 = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//teams//", Team2Arg.toLowerCase() + ".yml"));
            final File teamfile2 = new File("plugins//TeamPlugin//data//teams//" + Team2Arg.toLowerCase() + ".yml");

            List<String> allies1 = new ArrayList<>();
            allies1 = team1.getAllies();
            allies1.remove(Team2Arg.toLowerCase());

            List<String> allies2 = new ArrayList<>();
            allies2 = team2.getAllies();
            allies2.remove(Team1Arg.toLowerCase());

            cfg_teamdata1.set("Allies", allies1);
            cfg_teamdata2.set("Allies", allies2);

            try {
                cfg_teamdata1.save(teamfile1);
            } catch (IOException e){
                e.printStackTrace();
            }

            try {
                cfg_teamdata2.save(teamfile2);
            } catch (IOException e){
                e.printStackTrace();
            }

            team1.removeAlly(Team2Arg.toLowerCase());

            team2.removeAlly(Team1Arg.toLowerCase());

            //REMOVED ALLY
            for (Player players : Bukkit.getOnlinePlayers()){
                User u_players = TeamPlugin.instance.kgetUserManager().getUser(players);
                if (u_players.getTeam().equalsIgnoreCase(Team1Arg)){
                    final String prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.kgetConfig().Prefix);
                    players.sendMessage(prefix + TeamPlugin.instance.kgetMessages().No_Longer_Ally_Shout
                            .replace("%team%", team2.getOfficialName()));
                }
            }

            //REMOVED ALLY
            for (Player players : Bukkit.getOnlinePlayers()){
                User u_players = TeamPlugin.instance.kgetUserManager().getUser(players);
                if (u_players.getTeam().equalsIgnoreCase(Team2Arg)){
                    final String prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.kgetConfig().Prefix);
                    players.sendMessage(prefix + TeamPlugin.instance.kgetMessages().No_Longer_Ally_Shout
                            .replace("%team%", team1.getOfficialName()));
                }
            }


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

    public static void SetPlayerRank(Player player, String rank){

        final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//data//playerdata//", player.getUniqueId() + ".yml"));
        final File playerdata = new File("plugins//TeamPlugin//data//playerdata//" + player.getUniqueId() + ".yml");

        final YamlConfiguration cfg_ranksfile = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin", "roles.yml"));

        final String rankPrefix = String.valueOf(cfg_ranksfile.get("Roles."+rank+".Prefix"));
        final String rankDisplay = String.valueOf(cfg_ranksfile.get("Roles."+rank+".Display"));
        final int rankWeight = cfg_ranksfile.getInt("Roles."+rank+".Weight");

        cfg_playerdata.set("Role", rank);
        cfg_playerdata.set("Role_Prefix", rankPrefix);
        cfg_playerdata.set("Role_Display", rankDisplay);
        cfg_playerdata.set("Rank_Weight", rankWeight);

        try {
            cfg_playerdata.save(playerdata);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TeamPlugin.instance.kgetUserManager().removeUser(player);
        TeamPlugin.instance.kgetUserManager().addUser(player);

        User user = TeamPlugin.instance.kgetUserManager().getUser(player);

        user.setRole(rank);
        user.setRolePrefix(rankPrefix);
        user.setRoleDisplay(rankDisplay);
        user.setRoleWeight(rankWeight);

    }

}
