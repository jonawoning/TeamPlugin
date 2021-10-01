package nl.snowpix.teamplugin.system;

import nl.snowpix.teamplugin.TeamPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Config {

    public String Prefix;

    public String NoTeam_Name;
    public String NoTeam_Prefix;
    public String Global_Chat_Format;
    public String Team_Chat_Format;
    public Character Global_Letter;

    public boolean Use_Tab;
    public boolean Use_Chat_System;

    public HashMap<Player, String> Invites;

    public void ReloadConfig(){
        ReloadConfigData();
        TeamPlugin.instance.reloadConfig();
        TeamPlugin.instance.getConfig().options().copyDefaults();
        TeamPlugin.instance.saveDefaultConfig();
    }

    private void ReloadConfigData(){
        Prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Prefix"));
        NoTeam_Name = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("NoTeam_Name"));
        NoTeam_Prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("NoTeam_Prefix"));
        Global_Chat_Format = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Global_Chat_Format"));
        Team_Chat_Format = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Team_Chat_Format"));
        Global_Letter = TeamPlugin.instance.c.getString("Global_Letter").toCharArray()[0];

        Use_Tab = TeamPlugin.instance.c.getBoolean("Use_Tab");
        Use_Chat_System = TeamPlugin.instance.c.getBoolean("Use_Chat_System");

        Registerer.LoadEvents();
        TeamPlugin.instance.kgetSystemMethods().LoadAllTeams();

    }


}
