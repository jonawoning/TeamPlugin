package nl.snowpix.kingdom.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.api.Scoreboard;
import nl.snowpix.kingdom.api.ScoreboardB;
import nl.snowpix.kingdom.methods.ExtraMethods;
import nl.snowpix.kingdom.war.State;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KingdomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            User user = Kingdom.instance.kgetUserManager().getUser(player);

            final String player_kingdom = user.getKingdom();

            if (args.length == 0){

                Bukkit.broadcastMessage("Region > " + ExtraMethods.getRegion(player.getLocation()));

                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Use_Help_Command);

            }else{

                if (args[0].equalsIgnoreCase("help")){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &e&lKingdom &8&l&m-----" + "\n"
                            + "&d" + "\n"
                            + "&e/kingdom help &7- " + Kingdom.instance.kgetMessages().Help_Command_Main + "\n"
                            + "&e/kingdom join &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Join + "\n"
                            + "&e/kingdom leave &7- " + Kingdom.instance.kgetMessages().Help_Command_Leave + "\n"
                            + "&e/kingdom set &7(player) (kingdom) - " + Kingdom.instance.kgetMessages().Help_Command_Set + "\n"
                            + "&e/kingdom setrank &7(player) (rank) - " + Kingdom.instance.kgetMessages().Help_Command_Setrank + "\n"
                            + "&e/kingdom create &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Create + "\n"
                            + "&e/kingdom delete &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Delete + "\n"
                            + "&e/kingdom kick &7(player) &7- " + Kingdom.instance.kgetMessages().Help_Command_Kick + "\n"
                            + "&e/kingdom invite &7(player) &7- " + Kingdom.instance.kgetMessages().Help_Command_Invite + "\n"
                            + "&e/kingdom ally &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Ally + "\n"
                            + "&e/kingdom neutral &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Neutral + "\n"
                            + "&e/kingdom mutechannel &7(channel) &7- " + Kingdom.instance.kgetMessages().Help_Command_MuteChannel + "\n"
                            + "&e/kingdom setspawn &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Setspawn + "\n"
                            + "&e/kingdom spawn &7- " + Kingdom.instance.kgetMessages().Help_Command_Spawn + "\n"
                            + "&e/kingdom tphere &7- " + Kingdom.instance.kgetMessages().Help_Command_Tphere + "\n"
                            + "&e/kingdom info &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Info + "\n"
                            + "&e/kingdom war &7- " + Kingdom.instance.kgetMessages().Help_Command_War + "\n"
                            + "&e/kingdom version &7- " + Kingdom.instance.kgetMessages().Help_Command_Version + "\n"
                            + "&e/kingdom reload &7- " + Kingdom.instance.kgetMessages().Help_Command_Reload + "\n"
                            + "&e"));

                }

                else if (args[0].equalsIgnoreCase("create")){
                    if (player.hasPermission("kingdom.create")){
                        if (args.length > 1){

                            Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {

                                final String kingdom = args[1];
                                final YamlConfiguration cfg_kingdomfile = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", kingdom.toLowerCase() + ".yml"));
                                final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kingdom.toLowerCase() + ".yml");
                                final ArrayList<String> regionsstandaard = new ArrayList<>();
                                regionsstandaard.add(kingdom.toLowerCase());
                                regionsstandaard.add("other_region_change_this");

                                if (!kingdomfile.exists()){

                                    cfg_kingdomfile.set("Naam", kingdom);
                                    cfg_kingdomfile.set("Prefix", "&7[&c" + kingdom + "&7]");
                                    cfg_kingdomfile.set("Display", "&c" + kingdom);
                                    cfg_kingdomfile.set("Tab", "&7[&cKD&7] &c");
                                    cfg_kingdomfile.set("Soort", "Mens");
                                    cfg_kingdomfile.set("Regions", regionsstandaard);
                                    cfg_kingdomfile.set("Kills", 0);
                                    cfg_kingdomfile.set("W_Kills", 0);

                                    try {
                                        cfg_kingdomfile.save(kingdomfile);
                                    } catch (IOException e){
                                        e.printStackTrace();
                                    }

                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Create_Succesfull.replace("%kingdom%", kingdom));

                                    Kingdom.instance.kgetKingdomsMethods().LoadAllKingdoms();

                                }else{

                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Already_Exists);
                                }

                            });

                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom create (Kingdom Name)");
                        }

                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }

                else if (args[0].equalsIgnoreCase("delete")){

                    if (player.hasPermission("kingdom.delete")){

                        if (args.length > 1 && args[1] != null){

                            String kingdomarg = args[1].toLowerCase();
                            if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kingdomarg)){

                                Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {

                                    final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kingdomarg.toLowerCase() + ".yml");

                                    kingdomfile.delete();
                                    Kingdom.instance.kgetKingdomManager().removeKingdom(kingdomarg.toLowerCase());
                                    Kingdom.instance.kgetKingdomsMethods().Kingdoms.remove(kingdomarg);
                                    for (Player player1 : Bukkit.getOnlinePlayers()){
                                        User user1 = Kingdom.instance.kgetUserManager().getUser(player1);
                                        if (user1.getKingdom().equalsIgnoreCase(kingdomarg)){
                                            ExtraMethods.MakePlayerNO_KD(player1);
                                        }
                                    }

                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Succesfull_Deleted.replace("%kingdom%", kingdomarg));

                                });

                            }else{

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                            }

                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom delete (Kingdom)");
                        }

                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }

                }

                else if (args[0].equalsIgnoreCase("set")){
                    if (player.hasPermission("kingdom.set")){
                        if (args.length > 2){
                            if (args[2] != null){


                                final Player target = Bukkit.getPlayerExact(args[1]);
                                final String kingdomname = args[2].toLowerCase();
                                Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomname);

                                if (target != null){

                                    if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kingdomname)){

                                        ExtraMethods.SetPlayerKingdom(target, kingdomname);

                                        target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Set_Succes_Target.replace("%kingdom%", kingdoms.getOfficialName()).replace("%player%", player.getName()));
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Set_Succesfull.replace("%kingdom%", kingdoms.getOfficialName()).replace("%target%", target.getName()));

                                    }else{

                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);

                                    }
                                }else{

                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Incorrect_Player);

                                }

                            }else{

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom set (Player) (Kingdom)");

                            }
                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom set (Player) (Kingdom)");


                        }

                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }

                else if (args[0].equalsIgnoreCase("leave")){
                    if (player.hasPermission("kingdom.leave") || user.getPermissions().contains("kingdom.leave")){
                        ExtraMethods.MakePlayerNO_KD(player);
                        if (Kingdom.instance.kGetConfig().Commands_Leave != null){
                            for (String s : Kingdom.instance.kGetConfig().Commands_Leave){
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ChatColor.translateAlternateColorCodes('&', s.replace("%player%", player.getName())));
                            }
                        }

                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }

                }

                else if (args[0].equalsIgnoreCase("setrank")){

                    //IF THE PLAYER HAS THE PERMISSION:
                    if (player.hasPermission("kingdom.setrank")){
                        //PLAYER WITH THE PERMISSION BYPASSES THE CHECKS FOR SAME KINGDOM AND HIGHER/SAME RANK.

                        if (args.length > 2){
                            if (args[2] != null){
                                final Player target = Bukkit.getPlayerExact(args[1]);
                                final String setrankname = args[2];

                                if (target != null){
                                    if (Kingdom.instance.kgetRanks().ALL_RANKS.contains(args[2])){
                                        ExtraMethods.SetPlayerRank(target, args[2]);
                                        target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Rank_Set_Target.replace("%rank%", setrankname).replace("%player%", player.getName()));
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Rank_Set_Player.replace("%rank%", setrankname).replace("%target%", target.getName()));
                                    }else{
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Wrong_Rank);
                                    }
                                }else{
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom setrank (Player) (Rank)");
                                    return false;
                                }
                            }else{
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom setrank (Player) (Rank)");
                                return false;
                            }
                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom setrank (Player) (Rank)");
                            return false;
                        }
                    }else{

                        //IF THE PLAYER HAS THE KINGDOM.SETRANK PERMISSION IN RANKS.YML:
                        if (user.getPermissions().contains("kingdom.setrank")){

                            if (args.length > 2){

                                if (args[2] != null){

                                    final Player target = Bukkit.getPlayerExact(args[1]);
                                    final String setrankname = args[2];
                                    if (target != null){
                                        User u_target = Kingdom.instance.kgetUserManager().getUser(target);
                                        if (user.getKingdom().equalsIgnoreCase(u_target.getKingdom())){
                                            if (user.getRank_Weight() > u_target.getRank_Weight()){
                                                //IF THE PLAYER HAS HIGHER WEIGHT THEN OTHER PLAYER AND THE SAME KINGDOM.
                                                if (Kingdom.instance.kgetRanks().ALL_RANKS.contains(setrankname)){
                                                    //IF THE RANK IS FOUND IN RANKS.YML.
                                                    ExtraMethods.SetPlayerRank(target, args[2]);
                                                    target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Rank_Set_Target.replace("%rank%", setrankname).replace("%player%", player.getName()));
                                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Rank_Set_Player.replace("%rank%", setrankname).replace("%target%", target.getName()));

                                                }else{
                                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Wrong_Rank);
                                                }
                                            }else{
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Error_Higher_Rank);
                                            }
                                        }else{
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Not_Same_Kingdom);
                                        }
                                    }else{
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom setrank (Player) (Rank)");
                                        return false;
                                    }
                                }else{
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom setrank (Player) (Rank)");
                                    return false;
                                }

                            }else{
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom setrank (Player) (Rank)");
                                return false;
                            }
                        }else {
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                        }


                    }

                }

                else if (args[0].equalsIgnoreCase("invite")){

                    if (player.hasPermission("kingdom.invite") || user.getPermissions().contains("kingdom.invite")){

                        if (args.length > 1){

                            final Player target = Bukkit.getPlayerExact(args[1]);
                            if (target != null){
                                User u_target = Kingdom.instance.kgetUserManager().getUser(target);

                                if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){

                                    if (u_target.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){

                                        if (!Kingdom.instance.kGetConfig().invites.contains(player_kingdom + "-" + target.getUniqueId().toString())){

                                            Kingdom.instance.kGetConfig().invites.add(player_kingdom + "-" + target.getUniqueId().toString());

                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Invited_Succes.replace("%target%", target.getName()));
                                            target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Target_Invited_Succes.replace("%player%", player.getName()));

                                        }else{

                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Already_Invited_Target);
                                        }

                                    }else{

                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Need_NOKD);
                                    }
                                }else{

                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + "Error, kingdom needed.");
                                }

                            }else{

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom invite (Player)");
                            }

                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom invite (Player)");
                        }


                    }else{

                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);

                    }


                }else if (args[0].equalsIgnoreCase("join")){

                    if (args.length > 1){

                        final Player target = Bukkit.getPlayerExact(args[1]);

                        if (target != null){
                            User u_target = Kingdom.instance.kgetUserManager().getUser(target);
                            final String kingdomtarget = u_target.getKingdom();

                            if (Kingdom.instance.kGetConfig().invites.contains(kingdomtarget + "-" + player.getUniqueId().toString())){

                                ExtraMethods.SetPlayerKingdom(player, kingdomtarget);
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Succesfull_Joined_KD.replace("%kingdom%", kingdomtarget));

                                Kingdom.instance.kGetConfig().invites.remove(kingdomtarget + "-" + player.getUniqueId().toString());

                            }else{

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Did_Not_Invite);
                            }
                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Incorrect_Player);
                        }

                    }else{

                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom join (player)");
                    }

                }else if (args[0].equalsIgnoreCase("kick")){

                    if (player.hasPermission("kingdom.kick")){

                        if (args.length > 1){

                            final Player target = Bukkit.getPlayerExact(args[1]);
                            if (target != null){
                                User u_target = Kingdom.instance.kgetUserManager().getUser(target);

                                if (u_target.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Already_NOKD);
                                    return true;
                                }

                                ExtraMethods.MakePlayerNO_KD(target);
                                if (Kingdom.instance.kGetConfig().Commands_Leave != null){
                                    for (String s : Kingdom.instance.kGetConfig().Commands_Leave){
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ChatColor.translateAlternateColorCodes('&', s.replace("%player%", player.getName())));
                                    }
                                }
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Succesfull_Kicked_Player.replace("%target%", target.getName()));
                                target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Succesfull_Kicked_Target.replace("%player%", player.getName()));

                            }else{

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Incorrect_Player);
                            }
                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom kick (Player)");
                        }

                    }else{

                        //SEARCHING FOR THE PERMISSION IN THE PLAYER HIS RANK.
                        if (user.getPermissions().contains("kingdom.kick")){

                            if (args.length > 1){

                                final Player target = Bukkit.getPlayerExact(args[1]);

                                if (target != null){
                                    User u_target = Kingdom.instance.kgetUserManager().getUser(target);

                                    if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){

                                        if (user.getKingdom().equalsIgnoreCase(u_target.getKingdom())){

                                            if (user.getRank_Weight() > u_target.getRank_Weight()){

                                                ExtraMethods.MakePlayerNO_KD(target);
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Succesfull_Kicked_Player.replace("%target%", target.getName()));
                                                target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Succesfull_Kicked_Target.replace("%player%", player.getName()));

                                            }else{

                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Error_Higher_Rank);
                                            }

                                        }else{

                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Not_Same_Kingdom);
                                        }
                                    }else{

                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                                    }

                                }else{

                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Incorrect_Player);
                                }

                            }else{

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom kick (Player)");
                            }

                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                        }

                    }

                }else if (args[0].equalsIgnoreCase("mutechannel")){
                    if (player.hasPermission("kingdom.mutechannel")){
                        if (args.length > 1){

                            if (args[1].equalsIgnoreCase("public")){

                                if (Kingdom.instance.kGetConfig().Public_Chat){
                                    Kingdom.instance.kGetConfig().Public_Chat = false;
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Turned_Channel_On_Off.replace("%channel%", "Public").replace("%status%", ChatColor.RED + "OFF"));
                                }else{
                                    Kingdom.instance.kGetConfig().Public_Chat = true;
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Turned_Channel_On_Off.replace("%channel%", "Public").replace("%status%", ChatColor.GREEN + "ON"));
                                }
                            }else if (args[1].equalsIgnoreCase("roleplay")){

                                if (Kingdom.instance.kGetConfig().Roleplay_Chat){
                                    Kingdom.instance.kGetConfig().Roleplay_Chat = false;
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Turned_Channel_On_Off.replace("%channel%", "RolePlay").replace("%status%", ChatColor.RED + "OFF"));
                                }else{
                                    Kingdom.instance.kGetConfig().Roleplay_Chat = true;
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Turned_Channel_On_Off.replace("%channel%", "RolePlay").replace("%status%", ChatColor.GREEN + "ON"));
                                }
                            }else{

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom mutechannel (Channel)");
                                player.sendMessage("");
                                player.sendMessage("&8- &6Public");
                                player.sendMessage("&8- &6RolePlay");
                            }
                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom mutechannel (Channel)");
                            player.sendMessage("");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8- &6Public"));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8- &6RolePlay"));
                        }
                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }

                else if (args[0].equalsIgnoreCase("ally")){
                    if (user.getPermissions().contains("kingdom.ally") || player.hasPermission("kingdom.ally")){
                        if (args.length > 1) {
                            final String kingdomarg = args[1].toLowerCase();
                            final String kingdomplayer = user.getKingdom();
                            if (!kingdomplayer.equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)) {
                                if (!kingdomarg.equalsIgnoreCase(kingdomplayer)) {
                                    if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kingdomarg)) {

                                        Kingdoms kingdoms1 = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomplayer.toLowerCase());
                                        Kingdoms kingdoms2 = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomarg.toLowerCase());

                                        if (kingdoms1.getAllies().contains(kingdomarg)){
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Already_Ally
                                                    .replace("%kingdom%", kingdoms2.getOfficialName()));
                                        }else{

                                            ExtraMethods.AddAnAlly(kingdomplayer.toLowerCase(), kingdomarg.toLowerCase(), player);

                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {

                                                    Kingdom.instance.kgetKingdomManager().removeKingdom(kingdomplayer.toLowerCase());
                                                    Kingdom.instance.kgetKingdomManager().addKingdom(kingdomplayer.toLowerCase());
                                                }
                                            }.runTaskLaterAsynchronously(Kingdom.instance, 22);

                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Succesfull_Ally
                                                    .replace("%kingdom%", kingdoms2.getOfficialName()));

                                        }
                                    } else {
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                                    }
                                } else {
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Cant_Be_Your_KD);
                                }
                            } else {
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                            }
                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom ally (kingdom)");
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("neutral") || args[0].equalsIgnoreCase("neutraal")){
                    if (user.getPermissions().contains("kingdom.neutral") || player.hasPermission("kingdom.neutral")){
                        if (args.length > 1){
                            final String kingdomarg = args[1].toLowerCase();
                            final String kingdomplayer = user.getKingdom();
                            if (!kingdomplayer.equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
                                if (!kingdomarg.equalsIgnoreCase(kingdomplayer)){
                                    if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kingdomarg)){
                                        Kingdoms kingdoms1 = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomplayer.toLowerCase());
                                        Kingdoms kingdoms2 = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomarg.toLowerCase());

                                        if (!kingdoms1.getAllies().contains(kingdomarg)){
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Is_Not_An_Ally
                                                    .replace("%kingdom%", kingdoms2.getOfficialName()));
                                        }else{

                                            ExtraMethods.RemoveAnAlly(kingdomplayer.toLowerCase(), kingdomarg.toLowerCase(), player);

                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {

                                                    Kingdom.instance.kgetKingdomManager().removeKingdom(kingdomplayer.toLowerCase());
                                                    Kingdom.instance.kgetKingdomManager().addKingdom(kingdomplayer.toLowerCase());
                                                }
                                            }.runTaskLaterAsynchronously(Kingdom.instance, 22);

                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Succesfull_Neutral
                                                    .replace("%kingdom%", kingdoms2.getOfficialName()));

                                        }


                                    }else{
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                                    }

                                }else{
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Cant_Be_Your_KD);
                                }

                            }else{
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                            }
                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom neutral (kingdom)");
                        }
                    }
                }

                else if (args[0].equalsIgnoreCase("info")){
                    if (args.length > 1){
                        String kingdomarg = args[1].toLowerCase();
                        if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kingdomarg)){
                            Kingdoms kingdom = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomarg);
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', kingdom.getKingdomDisplay() + "&7:"));
                            if (kingdom.getAllies().size() > 0) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8> &7Allies: &a"));
                                for (String allies : kingdom.getAllies()) {
                                    Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(allies.toLowerCase());
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- " + kingdoms.getKingdomDisplay()));
                                }
                            }else{
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8> &7Allies: " + Kingdom.instance.kgetMessages().None));
                            }
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8> &7Kills: &a" + kingdom.getKills()));

                            //CHECK FOR THE PLAYER LIST.
                            final StringBuilder players = new StringBuilder();
                            int count = 0;
                            String king = Kingdom.instance.kgetMessages().None;

                            for (Player loopplayers : Bukkit.getOnlinePlayers()){

                                User loopuser = Kingdom.instance.kgetUserManager().getUser(loopplayers);

                                if (loopuser.getKingdom().equalsIgnoreCase(kingdomarg)){
                                    players.append(" ").append(loopplayers.getName());
                                    count++;
                                    if (loopuser.getRank().equalsIgnoreCase(Kingdom.instance.kGetConfig().King_Role))
                                        king = loopplayers.getName();
                                }

                                String string = "Hello, your rank is: %vault_prefix%";
                                player.sendMessage(PlaceholderAPI.setPlaceholders(player, string));

                            }

                            player.sendMessage(Kingdom.instance.kGetConfig().Info_Message.replace("%count_members%", String.valueOf(count)).
                                    replace("%members%", players));
                            if (Kingdom.instance.kGetConfig().See_King_Info)
                                player.sendMessage(Kingdom.instance.kGetConfig().King_Line.replace("%king%", king));

                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                        }
                    }else{
                        if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(user.getKingdom()) || !user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
                            Kingdoms kingdom = Kingdom.instance.kgetKingdomManager().getKingdom(user.getKingdom().toLowerCase());
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', kingdom.getKingdomDisplay() + "&7:"));
                            if (kingdom.getAllies().size() > 0) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8> &7Allies: &a"));
                                for (String allies : kingdom.getAllies()) {
                                    Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(allies.toLowerCase());
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- " + kingdoms.getKingdomDisplay()));
                                }
                            }else{
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8> &7Allies: " + Kingdom.instance.kgetMessages().None));
                            }
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8> &7Kills: &a" + kingdom.getKills()));

                            //CHECK FOR THE PLAYER LIST.
                            final StringBuilder players = new StringBuilder();
                            int count = 0;
                            String king = Kingdom.instance.kgetMessages().None;

                            for (Player loopplayers : Bukkit.getOnlinePlayers()){

                                User loopuser = Kingdom.instance.kgetUserManager().getUser(loopplayers);

                                if (loopuser.getKingdom().equalsIgnoreCase(user.getKingdom())){
                                    players.append(" ").append(loopplayers.getName());
                                    count++;
                                    if (loopuser.getRank().equalsIgnoreCase(Kingdom.instance.kGetConfig().King_Role))
                                        king = loopplayers.getName();
                                }

                            }

                            player.sendMessage(Kingdom.instance.kGetConfig().Info_Message.replace("%count_members%", String.valueOf(count)).
                                    replace("%members%", players));
                            if (Kingdom.instance.kGetConfig().See_King_Info)
                                player.sendMessage(Kingdom.instance.kGetConfig().King_Line.replace("%king%", king));
                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("list")){
                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', "&7Kingdoms: &a"));
                    for (String l : Kingdom.instance.kgetKingdomsMethods().Kingdoms){
                        Kingdoms kingdom = Kingdom.instance.kgetKingdomManager().getKingdom(l);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "- " + kingdom.getKingdomDisplay()));
                    }

                }
                else if (args[0].equalsIgnoreCase("setspawn")){
                    if (user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                        return true;
                    }
                    if (user.getPermissions().contains("kingdom.setspawn") || player.hasPermission("kingdom.setspawn")){
                        if (args.length > 1){
                            //ADMIN SETSPAWN
                            if (player.hasPermission("kingdom.opsetspawn") || user.getPermissions().contains("kingdom.opsetspawn")){
                                String arg1 = args[1].toLowerCase();
                                if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(arg1)){

                                    Kingdom.instance.kgetKingdomsMethods().SetAKingdomSpawn(arg1, player.getLocation());
                                    Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(arg1.toLowerCase());
                                    kingdoms.setKingdomSpawn(player.getLocation());
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Setspawn_Succesfull.replace("%kingdom%", StringUtils.capitalize(arg1)));
                                }else{
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                                }
                            }else{
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Setspawn_No_Perm_OP);
                            }
                        }else{
                            //DEFUALT SETSPAWN
                            Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(user.getKingdom().toLowerCase());
                            kingdoms.setKingdomSpawn(player.getLocation());
                            Kingdom.instance.kgetKingdomsMethods().SetAKingdomSpawn(user.getKingdom(), player.getLocation());
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Setspawn_Succesfull.replace("%kingdom%", user.getKingdom_Display()));
                        }

                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }

                }
                else if (args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("s")){
                    if (player.hasPermission("kingdom.spawn") || user.getPermissions().contains("kingdom.spawn")){
                        if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){

                            Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(user.getKingdom().toLowerCase());

                            if (kingdoms.getSpawn() == null){
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kGetConfig().SpawnNotExist);
                                return true;

                            }

                            if (!Kingdom.instance.kGetConfig().hspawner.containsKey(player)) {

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kGetConfig().SpawnTimer_Message);

                                BukkitTask runnable = new BukkitRunnable() {
                                    @Override
                                    public void run() {

                                        if (Kingdom.instance.kGetConfig().hspawner.containsKey(player)) {
                                            player.teleport(kingdoms.getSpawn());
                                            Kingdom.instance.kGetConfig().hspawner.remove(player);
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kGetConfig().Spawned_Message);
                                        }

                                    }
                                }.runTaskLater(Kingdom.instance, Kingdom.instance.kGetConfig().SpawnTime);

                                Kingdom.instance.kGetConfig().hspawner.put(player, runnable);
                            }else{
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kGetConfig().SpawnTimer_Already);
                            }
                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                        }

                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }

                }

                else if (args[0].equalsIgnoreCase("tphere")){

                    if (player.hasPermission("kingdom.tphere") || user.getPermissions().contains("kingdom.tphere")){

                        if (args.length > 1){

                            String kdarg = args[1];
                            if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kdarg)){

                                for (Player player1 : Bukkit.getOnlinePlayers()){
                                    User user1 = Kingdom.instance.kgetUserManager().getUser(player1);
                                    if (user1.getKingdom().equalsIgnoreCase(kdarg)){
                                        player1.teleport(player.getLocation());
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Teleported.replace("%kingdom%", kdarg));
                                    }
                                }

                            }else{

                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                            }

                        }else{

                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom tphere (kingdom)");
                        }

                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }
                else if (args[0].equalsIgnoreCase("version")){

                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', "&7Plugin information:" + "\n"
                    + "&7- Version: &e" + Kingdom.instance.kGetConfig().pluginversion + "\n"
                    + "&7- Author: &ePottenmaker" + "\n"
                    + "&7- Purchase at: &ehttps://discord.gg/wynFy2cVYJ &7| &eOr contact Jona#7797!" + "\n"));
                }

                else if (args[0].equalsIgnoreCase("war") || args[0].equalsIgnoreCase("oorlog")){
                    if (player.hasPermission("kingdom.war")){
                        if (Kingdom.instance.kGetConfig().state.equals(State.Status.Normal)){
                            Kingdom.instance.kgetKingdomsMethods().ResetAllKillsWorldWarK();
                            for (Player playerloops : Bukkit.getOnlinePlayers()){
                                Scoreboard.delete(playerloops);
                                Kingdom.instance.kGetConfig().state = State.Status.WorldWar;
                                ScoreboardB.setScoreBoard(playerloops);
                                playerloops.sendTitle(Kingdom.instance.kgetMessages().WorldWar_On_Title, Kingdom.instance.kgetMessages().WorldWar_On_SubTitle, 10, 30, 20);
                            }
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().WorldWar_On_Succes);
                            Bukkit.broadcastMessage(Kingdom.instance.kgetMessages().WorldWar_Announcement);
                            Kingdom.instance.kGetConfig().StartTimer();
                        }else{
                            for (Player playerloops : Bukkit.getOnlinePlayers()){
                                Scoreboard.delete(playerloops);
                                Kingdom.instance.kGetConfig().state = State.Status.Normal;
                                ScoreboardB.setScoreBoard(playerloops);
                                playerloops.sendTitle(Kingdom.instance.kgetMessages().WorldWar_Off_Title, Kingdom.instance.kgetMessages().WorldWar_Off_SubTitle, 10, 30, 20);
                            }
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().WorldWar_Off_Succes);
                            Bukkit.broadcastMessage(Kingdom.instance.kgetMessages().WorldWar_Announcement_Off);
                            Kingdom.instance.kGetConfig().reloadstimer();
                            Kingdom.instance.kgetKingdomsMethods().ResetAllKillsWorldWarK();
                        }
                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }
                else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")){
                    if (player.hasPermission("kingdom.reload")){
                        if (args.length > 1){
                            if (args[1].equalsIgnoreCase("kingdoms")){
                                Kingdom.instance.kgetKingdomsMethods().LoadAllKingdoms();
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdoms_Reloaded);
                            }else{
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom reload (opties)");
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &2Kingdoms"));
                            }
                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /kingdom reload (opties)");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7- &2Kingdoms"));
                        }
                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }

                else if (args[0].equalsIgnoreCase("anch")){

                    Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(user.getKingdom().toLowerCase());
                    player.sendMessage("Kingdom > " + user.getKingdom());
                    player.sendMessage("Kingdom Prefix > " + user.getKingdom_Prefix());
                    player.sendMessage("Kingdom Display > " + user.getKingdom_Display());
                    player.sendMessage("Kingdom Permissions > " + user.getPermissions());
                    if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
                        player.sendMessage("Kingdom Kingdom Prefix > " + kingdoms.getKingdomPrefix());
                        player.sendMessage("Kingdom Kingdom Name > " + kingdoms.getKingdomName());
                        player.sendMessage("Kingdom spawn > " + kingdoms.getSpawn());
                        player.sendMessage("Kingdom W Kills > " + kingdoms.getW_Kills());
                        player.teleport(kingdoms.getSpawn());
                        player.sendMessage("Allies" + kingdoms.getAllies());
                    }else{
                        player.sendMessage("Kingdom Kingdom Prefix > " + Kingdom.instance.kGetConfig().NoKingdom_Prefix);
                    }

                }
                else if (args[0].equalsIgnoreCase("test")){

                    Location location = player.getLocation();

                    for (int degree = 0; degree < 360; degree++) {
                        double radians = Math.toRadians(degree);
                        double x = Math.cos(radians);
                        double z = Math.sin(radians);
                        location.add(x,0,z);
                        location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 1);
                        location.subtract(x,0,z);
                    }

                }


                else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &e&lKingdom &8&l&m-----" + "\n"
                            + "&d" + "\n"
                            + "&e/kingdom help &7- " + Kingdom.instance.kgetMessages().Help_Command_Main + "\n"
                            + "&e/kingdom join &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Join + "\n"
                            + "&e/kingdom leave &7- " + Kingdom.instance.kgetMessages().Help_Command_Leave + "\n"
                            + "&e/kingdom set &7(player) (kingdom) - " + Kingdom.instance.kgetMessages().Help_Command_Set + "\n"
                            + "&e/kingdom setrank &7(player) (rank) - " + Kingdom.instance.kgetMessages().Help_Command_Setrank + "\n"
                            + "&e/kingdom create &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Create + "\n"
                            + "&e/kingdom delete &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Delete + "\n"
                            + "&e/kingdom kick &7(player) &7- " + Kingdom.instance.kgetMessages().Help_Command_Kick + "\n"
                            + "&e/kingdom invite &7(player) &7- " + Kingdom.instance.kgetMessages().Help_Command_Invite + "\n"
                            + "&e/kingdom ally &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Ally + "\n"
                            + "&e/kingdom neutral &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Neutral + "\n"
                            + "&e/kingdom mutechannel &7(channel) &7- " + Kingdom.instance.kgetMessages().Help_Command_MuteChannel + "\n"
                            + "&e/kingdom setspawn &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Setspawn + "\n"
                            + "&e/kingdom spawn &7- " + Kingdom.instance.kgetMessages().Help_Command_Spawn + "\n"
                            + "&e/kingdom tphere &7- " + Kingdom.instance.kgetMessages().Help_Command_Tphere + "\n"
                            + "&e/kingdom info &7(kingdom) &7- " + Kingdom.instance.kgetMessages().Help_Command_Info + "\n"
                            + "&e/kingdom war &7- " + Kingdom.instance.kgetMessages().Help_Command_War + "\n"
                            + "&e/kingdom version &7- " + Kingdom.instance.kgetMessages().Help_Command_Version + "\n"
                            + "&e/kingdom reload &7- " + Kingdom.instance.kgetMessages().Help_Command_Reload + "\n"
                            + "&e"));
                }


            }
        }
        return false;
    }
}
