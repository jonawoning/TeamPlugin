package nl.snowpix.teamplugin.data.TeamP;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private String teamName;
    private String officialName;
    private String teamPrefix;
    private List<Player> invites;



    public Team(String team){
        this.teamName = team;
        TeamGetter.getTeamName(team).thenAccept(result -> this.officialName = result);
        TeamGetter.getTeamPrefix(team).thenAccept(result -> this.teamPrefix = result);
        invites = new ArrayList<>();
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

    public void addInvite(Player player){
        invites.add(player);
    }

}
