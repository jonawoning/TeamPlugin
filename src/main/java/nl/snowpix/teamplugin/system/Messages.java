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
    public String Role_Does_Not_Exist;
    public String Succesfull_Changed_Some_Role;
    public String Ur_Role_Changed_Succesfully;
    public String Have_To_Be_In_A_Team;
    public String Casted_Ally_Message;
    public String Cannot_Own_Team;
    public String Already_An_Ally;
    public String Ally_Already_Sent;
    public String Ally_Request_Message;
    public String Ally_Request_Hover;
    public String Succesfully_Requested_Ally;
    public String Not_Requested_Alliance;
    public String No_Longer_Ally_Shout;
    public String U_Cant_Do_This_To_YourSelf;
    public String Have_To_Be_Higher_Role;
    public String Ur_Member_Now;
    public String Succes_Leaved_Team;
    public String Member_Leaved;

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
            cfg_messages.set("Role_Does_Not_Exist", "&cThis role doesn't exist, please fill in a correct one. &c&lThis can be case sensitive!!");
            cfg_messages.set("Succesfull_Changed_Some_Role", "&7You have &2succesfully &7changed &a%player% &7his role to &a%role%&7!");
            cfg_messages.set("Ur_Role_Changed_Succesfully", "&7Your role has been &2succesfully &7changed to &2%role%&7!");
            cfg_messages.set("Have_To_Be_In_A_Team", "&cYou have to be in a team to do this.");
            cfg_messages.set("Casted_Ally_Message", "&a&lYour team is now an ally with &2&l%team%&a&l!");
            cfg_messages.set("Cannot_Own_Team", "&cYou can't do this to your own team.");
            cfg_messages.set("Already_An_Ally", "&cYou are already an ally with this team.");
            cfg_messages.set("Ally_Already_Sent", "You have already sent this team an alliance request.");
            cfg_messages.set("Ally_Request_Message", "&aThe team &2%team% &ahas requested you to be his alliance! Click on this message to accept!");
            cfg_messages.set("Ally_Request_Hover", "&bClick here to accept the alliance request.");
            cfg_messages.set("Succesfully_Requested_Ally", "&aYou have succesfully requested an alliance with &2%team%&a!");
            cfg_messages.set("Not_Requested_Alliance", "&cThis team has not requested an alliance with your team.");
            cfg_messages.set("No_Longer_Ally_Shout", "&cYour team is no longer an alliance with the team &4%team%&c!");
            cfg_messages.set("U_Cant_Do_This_To_YourSelf", "&cYou can't do this to yourself..");
            cfg_messages.set("Have_To_Be_Higher_Role", "&cTo do this to this player, you need to have a higher role.");
            cfg_messages.set("Ur_Member_Now", "&cYou gave up your leadership to &4%player%&c, you are now a member.");
            cfg_messages.set("Succes_Leaved_Team", "&aYou have succesfully left your team.");
            cfg_messages.set("Member_Leaved", "&cYour team member named &4%player% &chas left your team!");

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
        Role_Does_Not_Exist = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Role_Does_Not_Exist"));
        Succesfull_Changed_Some_Role = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Succesfull_Changed_Some_Role"));
        Ur_Role_Changed_Succesfully = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Ur_Role_Changed_Succesfully"));
        Have_To_Be_In_A_Team = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Have_To_Be_In_A_Team"));
        Casted_Ally_Message = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Casted_Ally_Message"));
        Cannot_Own_Team = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Cannot_Own_Team"));
        Already_An_Ally = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Already_An_Ally"));
        Ally_Already_Sent = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Ally_Already_Sent"));
        Ally_Request_Message = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Ally_Request_Message"));
        Ally_Request_Hover = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Ally_Request_Hover"));
        Succesfully_Requested_Ally = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Succesfully_Requested_Ally"));
        Not_Requested_Alliance = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Not_Requested_Alliance"));
        No_Longer_Ally_Shout = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("No_Longer_Ally_Shout"));
        U_Cant_Do_This_To_YourSelf = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("U_Cant_Do_This_To_YourSelf"));
        Have_To_Be_Higher_Role = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Have_To_Be_Higher_Role"));
        Ur_Member_Now = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Ur_Member_Now"));
        Succes_Leaved_Team = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Succes_Leaved_Team"));
        Member_Leaved = ChatColor.translateAlternateColorCodes('&', cfg_messages.getString("Member_Leaved"));
    }

}
