package nl.snowpix.teamplugin.data.TeamP;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private String teamName;
    private String officialName;
    private String teamPrefix;
    private List<Player> invites;
    private List<String> allies;
    private List<String> allyVerzoeken;

    public Team(String team){
        this.teamName = team;
        TeamGetter.getTeamName(team).thenAccept(result -> this.officialName = result);
        TeamGetter.getTeamPrefix(team).thenAccept(result -> this.teamPrefix = result);
        invites = new ArrayList<>();
        TeamGetter.getTeamAllies(team).thenAccept(result -> this.allies = result);
        allyVerzoeken = new ArrayList<>();

    }

    public String getTeamName(){
        return teamName;
    }

    public String getOfficialName(){
        return officialName;
    }

    public String getTeamPrefix(){
        return teamPrefix;
    }

    public List<Player> getInvites(){
        return invites;
    }

    public List<String> getAllies(){
        return allies;
    }

    public List<String> getAllyVerzoeken(){
        return allyVerzoeken;
    }

    public void addAnAllyVerzoek(String kingdom){
        allyVerzoeken.add(kingdom.toLowerCase());
    }

    public void addInvite(Player player){
        invites.add(player);
    }

    public void addAlly(String teamName){
        allies.add(teamName.toLowerCase());
    }

    public void removeAlly(String teamName){
        allies.remove(teamName.toLowerCase());
    }

    public void removeAllyVerzoek(String teamName){
        allyVerzoeken.remove(teamName.toLowerCase());
    }

}
