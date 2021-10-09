package nl.snowpix.kingdom.data;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class User {

    private Player player;
    private String name;
    private UUID uuid;
    private String Kingdom;
    private String Kingdom_Prefix;
    private String Kingdom_Display;
    private String Rank;
    private String Rank_Prefix;
    private String Rank_Display;
    private String TabSuffix;
    private int Rank_Weight;
    private String Tab;
    private String Soort;
    private int Points;
    private List<String> permissions;

    public User(Player player) {
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        Getters.getPlayerKD(player).thenAccept(result -> this.Kingdom = result);
        Getters.getPlayerKD_Prefix(player).thenAccept(result -> this.Kingdom_Prefix = result);
        Getters.getPlayerKD_Display(player).thenAccept(result -> this.Kingdom_Display = result);
        Getters.getPlayerRank(player).thenAccept(result -> this.Rank = result);
        Getters.getPlayerRankPrefix(player).thenAccept(result -> this.Rank_Prefix = result);
        Getters.getPlayerRankDisplay(player).thenAccept(result -> this.Rank_Display = result);
        Getters.getPlayerTabSuffix(player).thenAccept(result -> this.TabSuffix = result);
        Getters.getPlayerRankWeight(player).thenAccept(result -> this.Rank_Weight = result);
        Getters.getPlayerTab(player).thenAccept(result -> this.Tab = result);
        Getters.getPlayerSoort(player).thenAccept(result -> this.Soort = result);
        Getters.getPlayerPoints(player).thenAccept(result -> this.Points = result);
        Getters.getPlayerPermissions(player).thenAccept(result -> this.permissions = result);
    }

    public UUID getUUID(){
        return uuid;
    }

    public Player getPlayer(){
        return player;
    }

    public String getName(){
        return name;
    }

    public String getKingdom(){
        return Kingdom;
    }

    public String getKingdom_Prefix(){
        return Kingdom_Prefix;
    }

    public String getKingdom_Display(){
        return Kingdom_Display;
    }

    public String getRank(){
        return Rank;
    }

    public String getRank_Prefix(){
        return Rank_Prefix;
    }

    public String getRank_Display(){
        return Rank_Display;
    }

    public String getTabSuffix(){
        return TabSuffix;
    }

    public List<String> getPermissions(){
        return permissions;
    }

    public int getRank_Weight(){
        return Rank_Weight;
    }

    public String getTab(){
        return Tab;
    }

    public String getSoort(){
        return Soort;
    }

    public int getPoints(){
        return Points;
    }





}
