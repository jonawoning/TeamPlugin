package nl.snowpix.teamplugin.methods;

import nl.snowpix.teamplugin.TeamPlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;

public class SystemMethods {

    public ArrayList<String> Teams = new ArrayList<>();

    public void LoadAllTeams(){
        Bukkit.getScheduler().runTaskAsynchronously(TeamPlugin.instance, () -> {
            Teams.clear();
            final File teamData = new File("plugins//TeamPlugin//data//teams");
            if (teamData.listFiles() != null){
                for(File p : teamData.listFiles()) {
                    final String teamName = p.getName().replace(".yml", "");

                    Teams.add(teamName.toLowerCase());
                    TeamPlugin.instance.kgetTeamManager().removeTeam(teamName.toLowerCase());
                    TeamPlugin.instance.kgetTeamManager().addTeam(teamName.toLowerCase());

                }
            }
        });

        System.out.println("[TeamPlugin] All teams are succesfully loaded.");
    }

    public boolean CheckTeam(String team){
        return Teams.contains(team.toLowerCase());
    }

}
