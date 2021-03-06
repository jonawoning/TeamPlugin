package nl.snowpix.teamplugin.system;

import nl.snowpix.teamplugin.TeamPlugin;
import org.bukkit.ChatColor;

public class Config {

    public String Prefix;

    public String NoTeam_Name;
    public String NoTeam_Prefix;
    public String NoTeam_Role;
    public String NoTeam_Role_Prefix;
    public String Global_Chat_Format;
    public String Team_Chat_Format;
    public Character Global_Letter;
    public String Member_Rank;
    public String Leader_Rank;
    public String CoLeader_Rank;
    public String Ally_Color;
    public String Team_Color;
    public String Other_Color;

    public boolean Use_Tab;
    public boolean Use_Chat_System;

    public void ReloadConfig(){
        ReloadConfigData();
        TeamPlugin.instance.reloadConfig();
        TeamPlugin.instance.getConfig().options().copyDefaults();
        TeamPlugin.instance.saveDefaultConfig();
    }

    private void ReloadConfigData(){
        Prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Prefix"));
        NoTeam_Name = TeamPlugin.instance.c.getString("NoTeam_Name");
        NoTeam_Role = TeamPlugin.instance.c.getString("NoTeam_Role");
        NoTeam_Prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("NoTeam_Prefix"));
        NoTeam_Role_Prefix = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("NoTeam_Role_Prefix"));
        Global_Chat_Format = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Global_Chat_Format"));
        Team_Chat_Format = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Team_Chat_Format"));
        Global_Letter = TeamPlugin.instance.c.getString("Global_Letter").toCharArray()[0];
        Member_Rank = TeamPlugin.instance.c.getString("Member_Rank");
        Leader_Rank = TeamPlugin.instance.c.getString("Leader_Rank");
        CoLeader_Rank = TeamPlugin.instance.c.getString("CoLeader_Rank");
        Ally_Color = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Ally_Color"));
        Team_Color = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Team_Color"));
        Other_Color = ChatColor.translateAlternateColorCodes('&', TeamPlugin.instance.c.getString("Other_Color"));

        Use_Tab = TeamPlugin.instance.c.getBoolean("Use_Tab");
        Use_Chat_System = TeamPlugin.instance.c.getBoolean("Use_Chat_System");

        Registerer.LoadEvents();
        TeamPlugin.instance.kgetSystemMethods().LoadAllTeams();

    }


}
