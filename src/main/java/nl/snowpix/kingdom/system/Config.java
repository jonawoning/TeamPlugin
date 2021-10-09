package nl.snowpix.kingdom.system;

import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.api.Scoreboard;
import nl.snowpix.kingdom.api.ScoreboardB;
import nl.snowpix.kingdom.events.BlockBreakMine;
import nl.snowpix.kingdom.war.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public class Config {

    public String Prefix;
    public List<String> JoinMessage;
    public String Donator_Perm;
    public String Staff_Perm;
    public String Admin_Perm;
    public String NoKingdom_Name;
    public String NoKingdom_Prefix;
    public String NoKingdom_Display;
    public String NoKingdom_Tab;
    public String NoKingdom_Rank;
    public String NoKingdom_Rank_Prefix;
    public String NoKingdom_Rank_Display;
    public String NoKingdom_Soort;
    public String kingdom_format;
    public String public_format;
    public String roleplay_format;
    public String staff_format;
    public String roleplay_channel_perm;
    public String staff_channel_perm;
    public String RegionEnter;
    public String RegionLeave;
    public String OwnKingdomRegionEnter;
    public String OwnKingdomRegionLeave;
    public String RegionEnter_Title;
    public String RegionEnter_SubTitle;
    public String RegionLeave_Title;
    public String RegionLeave_SubTitle;
    public String OwnKD_Region_EN_TITLE;
    public String OwnKD_Region_EN_SUBTITLE;
    public String OwnKD_Region_LE_TITLE;
    public String OwnKD_Region_LE_SUBTITLE;
    public String Wilderniss_Name;
    public Character public_letter;
    public Character roleplay_letter;
    public Character staff_letter;
    public String Member_Rank;
    public String n_Scoreboard_Title;
    public List<String> n_Scoreboard_Lines;
    public String w_Scoreboard_Title;
    public List<String> w_Scoreboard_Lines;
    public String Kingdom_Format_War;
    public String DeathMessage;
    public String DeathMessage_Other;
    public String King_Role;
    public String Info_Message;
    public String King_Line;
    public String pluginversion;
    public String Spawned_Message;
    public String SpawnTimer_Message;
    public String SpawnTimer_Already;
    public String SpawnTimer_Move;
    public String SpawnNotExist;
    public boolean See_King_Info;
    public boolean Public_Chat;
    public boolean Roleplay_Chat;
    public boolean MessageOnEveryRegion;
    public boolean UseRegionEvents;
    public boolean Use_Scoreboard;
    public boolean SpawnOnDeath;
    public boolean Use_Mineisland;
    public ArrayList<String> invites = new ArrayList<>();
    public State.Status state;
    public String onlineServer = "https://snowpix.nl/verify.php";
    public int SpawnTime;
    public int stimer;
    public int Timer_Seconds;

    public Map<Player, BukkitTask> hspawner = new HashMap<>();
    public HashMap<String, Integer> War_Kills_PerKD;

    public boolean Use_Chat;

    public ArrayList<Player> staffmode = new ArrayList<>();
    public List<String> Commands_Leave = new ArrayList<>();

    private String securitykey = "YecoF0I6M05thxLeokoHuW8iUhTdIUInjkfF";

    public void ReloadConfig(){
        ReloadConfigData();
        Kingdom.instance.reloadConfig();
        Kingdom.instance.getConfig().options().copyDefaults();
        Kingdom.instance.saveDefaultConfig();
    }

    private void ReloadConfigData(){
        PluginDescriptionFile pdf = Kingdom.instance.getDescription();
        Prefix = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("Prefix"));
        JoinMessage = Kingdom.instance.c.getStringList("JoinMessage");
        Donator_Perm = Kingdom.instance.c.getString("Donator_Perm");
        Staff_Perm = Kingdom.instance.c.getString("Staff_Perm");
        Admin_Perm = Kingdom.instance.c.getString("Admin_Perm");
        NoKingdom_Name = Kingdom.instance.c.getString("NoKingdom_Name");
        NoKingdom_Prefix = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("NoKingdom_Prefix"));
        NoKingdom_Display = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("NoKingdom_Display"));
        NoKingdom_Tab = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("NoKingdom_Tab"));
        NoKingdom_Rank = Kingdom.instance.c.getString("NoKingdom_Rank");
        NoKingdom_Rank_Prefix = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("NoKingdom_Rank_Prefix"));
        NoKingdom_Rank_Display = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("NoKingdom_Rank_Display"));
        NoKingdom_Soort = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("NoKingdom_Soort"));
        Use_Chat = Kingdom.instance.c.getBoolean("Use_Chat");
        UseRegionEvents = Kingdom.instance.c.getBoolean("UseRegionEvents");
        Use_Scoreboard = Kingdom.instance.c.getBoolean("Use_Scoreboard");
        kingdom_format = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("kingdom_format"));
        public_format = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("public_format"));
        public_letter = Kingdom.instance.c.getString("public_letter").toCharArray()[0];
        roleplay_format = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("roleplay_format"));
        roleplay_letter = Kingdom.instance.c.getString("roleplay_letter").toCharArray()[0];
        roleplay_channel_perm = Kingdom.instance.c.getString("roleplay_channel_perm");
        staff_format = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("staff_format"));
        staff_letter = Kingdom.instance.c.getString("staff_letter").toCharArray()[0];
        staff_channel_perm = Kingdom.instance.c.getString("staff_channel_perm");
        Member_Rank = Kingdom.instance.c.getString("Member_Rank");
        MessageOnEveryRegion = Kingdom.instance.c.getBoolean("MessageOnEveryRegion");
        RegionEnter = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("RegionEnter"));
        RegionLeave = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("RegionLeave"));
        OwnKingdomRegionEnter = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("OwnKingdomRegionEnter"));
        OwnKingdomRegionLeave = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("OwnKingdomRegionLeave"));
        RegionEnter_Title = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("RegionEnter_Title"));
        RegionEnter_SubTitle = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("RegionEnter_SubTitle"));
        RegionLeave_Title = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("RegionLeave_Title"));
        RegionLeave_SubTitle = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("RegionLeave_SubTitle"));
        OwnKD_Region_EN_TITLE = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("OwnKD_Region_EN_TITLE"));
        OwnKD_Region_EN_SUBTITLE = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("OwnKD_Region_EN_SUBTITLE"));
        OwnKD_Region_LE_TITLE = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("OwnKD_Region_LE_TITLE"));
        OwnKD_Region_LE_SUBTITLE = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("OwnKD_Region_LE_SUBTITLE"));
        Wilderniss_Name = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("Wilderniss_Name"));
        n_Scoreboard_Title = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("n_Scoreboard_Title"));
        n_Scoreboard_Lines = Kingdom.instance.c.getStringList("n_Scoreboard_Lines");
        w_Scoreboard_Title = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("w_Scoreboard_Title"));
        w_Scoreboard_Lines = Kingdom.instance.c.getStringList("w_Scoreboard_Lines");
        Kingdom_Format_War = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("Kingdom_Format_War"));
        DeathMessage = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("DeathMessage"));
        DeathMessage_Other = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("DeathMessage_Other"));
        See_King_Info = Kingdom.instance.c.getBoolean("See_King_Info");
        King_Role = Kingdom.instance.c.getString("King_Role");
        Info_Message = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("Info_Message"));
        King_Line = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("King_Line"));
        SpawnTime = Kingdom.instance.c.getInt("SpawnTime");
        Spawned_Message = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("Spawned_Message"));
        SpawnTimer_Message = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("SpawnTimer_Message"));
        SpawnTimer_Already = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("SpawnTimer_Already"));
        SpawnTimer_Move = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("SpawnTimer_Move"));
        SpawnNotExist = ChatColor.translateAlternateColorCodes('&', Kingdom.instance.c.getString("SpawnNotExist"));
        SpawnOnDeath = Kingdom.instance.c.getBoolean("SpawnOnDeath");
        Timer_Seconds = Kingdom.instance.c.getInt("Timer_Seconds");
        Use_Mineisland = Kingdom.instance.c.getBoolean("Use_Mineisland");
        BlockBreakMine.ReloadMineDataYML();
        pluginversion = pdf.getVersion();
        stimer = 0;
        War_Kills_PerKD = new HashMap<String, Integer>();
        Commands_Leave = Kingdom.instance.c.getStringList("Commands_Leave");

        Kingdom.instance.kgetPointsConfig().LoadAllVariables();

        Public_Chat = true;
        Roleplay_Chat = true;
        state = State.Status.Normal;

        staffmode.clear();
        invites.clear();

        ScoreboardB.CheckIfSB();
        ScoreboardB.UpdateNameTags();
        Kingdom.instance.kgetPointsConfig().LoopPoints();
    }

    public void CheckPlugin(String pluginName){
        if (pluginName.equalsIgnoreCase("WGRegionEvents")){
            if (getServer().getPluginManager().getPlugin("WGRegionEvents")!=null){
                return;
            }else{
                System.out.println(SystemColors.TEXT_YELLOW + "[KINGDOM] " + SystemColors.TEXT_RED + "YOU NEED TO INSTALL THE PLUGIN: " + pluginName + ", OR THE PLUGIN WILL NOT WORK!" + SystemColors.TEXT_RESET);
                System.out.println(SystemColors.TEXT_RED + "You can download WGRegionEvents here: https://www.mediafire.com/file/yuj2i2t5ph6azpl/WGRegionEvents-1.2.1.jar/file");
            }
        }else{
            if (getServer().getPluginManager().getPlugin(pluginName)!=null){
                return;
            }else{
                System.out.println(SystemColors.TEXT_YELLOW + "[KINGDOM] " + SystemColors.TEXT_RED + "YOU NEED TO INSTALL THE PLUGIN: " + pluginName + ", OR THE PLUGIN WILL NOT WORK!" + SystemColors.TEXT_RESET);
            }
        }
    }

    public void CheckPlayers(){
        if (Bukkit.getOnlinePlayers().size() != 0){
            for (Player player : Bukkit.getOnlinePlayers()){
                Kingdom.instance.kgetUserManager().addUser(player);
            }

        }
    }

    public void reloadstimer(){
        stimer = 0;
    }

    public void StartTimer(){
        stimer = Timer_Seconds;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Kingdom.instance.kGetConfig().state.equals(State.Status.WorldWar)){
                    if (stimer != 0){
                        stimer--;
                    }else{
                        if (Kingdom.instance.kGetConfig().state.equals(State.Status.WorldWar)){

                            for (Player playerloops : Bukkit.getOnlinePlayers()){
                                Scoreboard.delete(playerloops);
                                Kingdom.instance.kGetConfig().state = State.Status.Normal;
                                ScoreboardB.setScoreBoard(playerloops);
                            }
                            Bukkit.broadcastMessage(Kingdom.instance.kgetMessages().WorldWar_Announcement_Off);
                            cancel();
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Kingdom.instance, 0L, 25L);
    }

}
