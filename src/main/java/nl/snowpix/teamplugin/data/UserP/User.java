package nl.snowpix.teamplugin.data.UserP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class User {

    private Player player;
    private String name;
    private UUID uuid;
    private String team;
    private String team_prefix;
    private String role;
    private String rolePrefix;
    private String roleDisplay;
    private int roleWeight;
    private List<String> permissions;

    public User (Player player){
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        UserGetter.getPlayerTeam(player).thenAccept(result -> this.team = result);
        UserGetter.getPlayerTeam_Prefix(player).thenAccept(result -> this.team_prefix = result);
        UserGetter.getPlayerRole(player).thenAccept(result -> this.role = result);
        UserGetter.getPlayerRole_Prefix(player).thenAccept(result -> this.rolePrefix = result);
        UserGetter.getPlayerRole_Weight(player).thenAccept(result -> this.roleWeight = result);
        UserGetter.getPlayerRole_Display(player).thenAccept(result -> this.roleDisplay = result);
        UserGetter.getPlayerPermissions(player).thenAccept(result -> this.permissions = result);
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

    public String getRole(){
        return role;
    }

    public String getRolePrefix(){
        return ChatColor.translateAlternateColorCodes('&', rolePrefix);
    }

    public String getRoleDisplay(){
        return roleDisplay;
    }

    public int getRoleWeight(){
        return roleWeight;
    }

    public List<String> getPermissions(){
        return permissions;
    }

    public void setTeam_Name(String name){
        team_prefix = name;
    }

    public void setTeam_Prefix(String prefix){
        team_prefix = ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public void setRole(String roleName){
        role = roleName;
    }

    public void setRolePrefix(String rolePrefix1){
        rolePrefix = ChatColor.translateAlternateColorCodes('&', rolePrefix1);
    }

    public void setRoleDisplay(String roleDisplay1){
        roleDisplay = ChatColor.translateAlternateColorCodes('&', roleDisplay1);
    }

    public void setRoleWeight(int weight){
        roleWeight = weight;
    }

    public boolean hasPermission(String permission){
        return permissions.contains(permission);
    }

}
