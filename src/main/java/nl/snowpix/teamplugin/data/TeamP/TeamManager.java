package nl.snowpix.teamplugin.data.TeamP;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {

    private Set<Team> teams = new HashSet<>();

    public Set<Team> getTeams() {
        return teams;
    }

    public Team getTeam(String team) {
        return teams.stream().filter(teams -> teams.getTeamName().equals(team)).findAny().orElse(null);
    }

    public void addTeam(String team) {
        teams.add(new Team(team));
    }

    public void removeTeam(String team) {
        teams.remove(team);
    }

}
