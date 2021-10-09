package nl.snowpix.teamplugin.system;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Messages {

    private YamlConfiguration cfg_messages = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin//", "messages.yml"));
    private File messages = new File("plugins//TeamPlugin//messages.yml");

    public String No_Permission;
    public String Player_Not_Exists;
    public String Other_Player;
    public String Succesfully_Invited_P;
    public String Invited_Message;
    public String Team_Not_Exists;
    public String ToMuch_Chars;
    public String ToMuch_Chars_Perm;
    public String TeamName_Chars;
    public String TeamHasBeenMade;
    public String TeamAlreadyExists;
    public String TeamHasBeenDeleted;
    public String PlayerTeamLess;
    public String KickedOutOfUTeam;
    public String UhaveBeenSetTeam;
    public String HasBeenSuccesfullSet;
    public String CantBeInATeam;
    public String Invite_Hover_Text;
    public String Invite_Button_Hover;
    public String You_Already_In_A_Team;
    public String Have_To_Be_Same_Team;

    public void checkMessages() {
        if (!messages.exists()) {
            cfg_messages.set("No_Permission", "&cYou have no permission to use this.");
            cfg_messages.set("Player_Not_Exists", "&cThis player doesn't exist.");
            cfg_messages.set("Other_Player", "&cPlease fill in a other player.");
            cfg_messages.set("Succesfully_Invited_P", "&7You just succesfully invited &a%player% &7for your team!");
            cfg_messages.set("Invited_Message", "&7You have been invited for &2%team%&7!");
            cfg_messages.set("Team_Not_Exists", "&cThis team doesn't exist.");
            cfg_messages.set("ToMuch_Chars", "&cSorry, but you can't use this much characters. Without a &5package &cuse 1 up to 3 and with a package choose 1 up to 5.");
            cfg_messages.set("ToMuch_Chars_Perm", "&cYou can only use 3 charachters, with a package you can use 5.");
            cfg_messages.set("TeamName_Chars", "&cFor a team you can only enter &412 &ccharacters..");
            cfg_messages.set("TeamHasBeenMade", "&7The team %team% &7has been succesfully created!");
            cfg_messages.set("TeamAlreadyExists", "&cThis team already exists.");
            cfg_messages.set("TeamHasBeenDeleted", "&7The team &2%team% &7has been succesfully deleted.");
            cfg_messages.set("PlayerTeamLess", "&7The player &2%player% &7has been set to teamless.");
            cfg_messages.set("KickedOutOfUTeam", "&c&lYou have been kicked out of your team!");
            cfg_messages.set("UhaveBeenSetTeam", "&7You have been set into the team &2%team%&7!");
            cfg_messages.set("HasBeenSuccesfullSet", "&2%player% &7has succesfully been set into the team &2%team%&7.");
            cfg_messages.set("CantBeInATeam", "&cYou can't be in a team to do this.");
            cfg_messages.set("Invite_Hover_Text", "&aClick here to accept the invite.");
            cfg_messages.set("Invite_Button_Hover", "&a&l[&a&l&n&oACCEPT THE INVITE&a&l]&7");
            cfg_messages.set("You_Already_In_A_Team", "&cYou are already member of a team!");
            cfg_messages.set("Have_To_Be_Same_Team", "&cYou can only kick players that are in the same team!");

            try {
                cfg_messages.save(messages);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(SystemColors.TEXT_YELLOW + "[TEAMPLUGIN] " + SystemColors.TEXT_GREEN + "The file messages.yml is succesfully created!" + SystemColors.TEXT_RESET);
        }
    }

    public void reloadMessages(){
        No_Permission = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("No_Permission"));
        Player_Not_Exists = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Player_Not_Exists"));
        Other_Player = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Other_Player"));
        Succesfully_Invited_P = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Succesfully_Invited_P"));
        Invited_Message = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Invited_Message"));
        Team_Not_Exists = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Team_Not_Exists"));
        ToMuch_Chars = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("ToMuch_Chars"));
        ToMuch_Chars_Perm = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("ToMuch_Chars_Perm"));
        TeamName_Chars = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("TeamName_Chars"));
        TeamHasBeenMade = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("TeamHasBeenMade"));
        TeamAlreadyExists = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("TeamAlreadyExists"));
        TeamHasBeenDeleted = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("TeamHasBeenDeleted"));
        PlayerTeamLess = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("PlayerTeamLess"));
        KickedOutOfUTeam = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("KickedOutOfUTeam"));
        UhaveBeenSetTeam = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("UhaveBeenSetTeam"));
        HasBeenSuccesfullSet = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("HasBeenSuccesfullSet"));
        CantBeInATeam = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("CantBeInATeam"));
        Invite_Hover_Text = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Invite_Hover_Text"));
        Invite_Button_Hover = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Invite_Button_Hover"));
        You_Already_In_A_Team = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("You_Already_In_A_Team"));
        Have_To_Be_Same_Team = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Have_To_Be_Same_Team"));
    }

}
