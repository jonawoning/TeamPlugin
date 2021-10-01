package nl.snowpix.teamplugin.data.UserP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private Player player;
    private String name;
    private UUID uuid;
    private String team;
    private String team_prefix;

    public User (Player player){
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        UserGetter.getPlayerTeam(player).thenAccept(result -> this.team = result);
        UserGetter.getPlayerTeam_Prefix(player).thenAccept(result -> this.team_prefix = result);
    }

    public Player getPlayer(){
        return player;
    }

    public String getName(){
        return name;
    }

    public UUID getUUID(){
        return uuid;
    }

    public String getTeam(){
        return team;
    }

    public String getTeam_prefix(){
        return team_prefix;
    }

    public void setTeam_Name(String name){
        team_prefix = name;
    }

    public void setTeam_Prefix(String prefix){
        team_prefix = ChatColor.translateAlternateColorCodes('&', prefix);
    }

}
