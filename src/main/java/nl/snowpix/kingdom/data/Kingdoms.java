package nl.snowpix.kingdom.data;

import nl.snowpix.kingdom.Kingdom;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

public class Kingdoms {

    private String kingdomName;
    private String officialName;
    private String kingdomPrefix;
    private String kingdomDisplay;
    private String kingdomTab;
    private String soort;
    private List<String> regions;
    private List<String> allies;
    private int kills;
    private int w_kills;
    private Location spawn;



    public Kingdoms(String kingdom){
        if (!kingdom.equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
            this.kingdomName = kingdom;
            GetterKingdoms.getKingdomName(kingdom).thenAccept(result -> this.officialName = result);
            GetterKingdoms.getKingdomPrefix(kingdom).thenAccept(result -> this.kingdomPrefix = result);
            GetterKingdoms.getKingdomDisplay(kingdom).thenAccept(result -> this.kingdomDisplay = result);
            GetterKingdoms.getKingdomTab(kingdom).thenAccept(result -> this.kingdomTab = result);
            GetterKingdoms.getKingdomSoort(kingdom).thenAccept(result -> this.soort = result);
            GetterKingdoms.getKingdomRegions(kingdom).thenAccept(result -> this.regions = result);
            GetterKingdoms.getKingdomAllies(kingdom).thenAccept(result -> this.allies = result);
            GetterKingdoms.getKingdomKills(kingdom).thenAccept(result -> this.kills = result);
            GetterKingdoms.getKingdomKills_W(kingdom).thenAccept(result -> this.w_kills = result);
            GetterKingdoms.getKingdomSpawn(kingdom).thenAccept(result -> this.spawn = result);
        }else{
            this.officialName = Kingdom.instance.kGetConfig().NoKingdom_Name;
            this.kingdomPrefix = Kingdom.instance.kGetConfig().NoKingdom_Prefix;
            this.kingdomDisplay = Kingdom.instance.kGetConfig().NoKingdom_Display;
            this.kingdomTab = Kingdom.instance.kGetConfig().NoKingdom_Tab;
            this.soort = Kingdom.instance.kGetConfig().NoKingdom_Soort;
            this.regions = Collections.singletonList("NoRegionForTheNoKingdom");
            this.allies = null;
            this.kills = 0;
            this.w_kills = 0;
            this.spawn = null;

        }
    }

    public String getKingdomName(){
        return kingdomName;
    }

    public String getOfficialName(){
        return officialName;
    }


    public String getKingdomPrefix(){
        return kingdomPrefix;
    }

    public String getKingdomDisplay(){
        return kingdomDisplay;
    }

    public String getKingdomTab(){
        return kingdomTab;
    }

    public String getSoort(){
        return soort;
    }

    public List<String> getRegions(){
        return regions;
    }

    public List<String> getAllies(){
        return allies;
    }

    public int getKills(){
        return kills;
    }

    public int getW_Kills(){
        return w_kills;
    }

    public Location getSpawn(){
        return spawn;
    }

    public void addW_kill(){
        w_kills++;
    }

    public void add_kill(){
        kills++;
    }

    public void setKingdomSpawn(Location location){
        spawn = location;
    }

    public void setKingdomPrefix(String prefix){
        kingdomPrefix = ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public void setKingdomDisplay(String display){
        kingdomDisplay = ChatColor.translateAlternateColorCodes('&', display);
    }

    public void setKingdomTab(String tab){
        kingdomTab = ChatColor.translateAlternateColorCodes('&', tab);
    }

    public void setSoort(String soort1){
        soort = soort1;
    }


}
