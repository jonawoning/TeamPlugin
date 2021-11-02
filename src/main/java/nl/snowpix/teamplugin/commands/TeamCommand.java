package nl.snowpix.teamplugin.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.snowpix.teamplugin.TeamPlugin;
import nl.snowpix.teamplugin.data.TeamP.Team;
import nl.snowpix.teamplugin.data.UserP.User;
import nl.snowpix.teamplugin.methods.TeamMethods;
import nl.snowpix.teamplugin.system.SystemColors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0){

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &d&lTeams &8&l&m-----" + "\n"
                    + "&d" + "\n"
                    + "&5/teams invite &7(player) - Invite a player to your team." + "\n"
                    + "&5/teams kick &7(player) - Kick a player out of your team." + "\n"
                    + "&5/teams setrole &7(player) (role) - Set a player his role." + "\n"
                    + "&5/teams ally &7(team) - Request an alliance with a team." + "\n"
                    + "&5/teams accept &7(team) - Accept an alliance request." + "\n"
                    + "&5/teams neutral &7(team) - Set a team to neutral." + "\n"
                    + "&5/teams create &7(team) (tag) - Create a team." + "\n"
                    + "&5/teams delete &7(team) - Delete a team." + "\n"
                    + "&5/teams set &7(player) (team) - Set a player in a team." + "\n"
                    + "\n"
                    + "&e"));

        }else{
            if (args[0].equalsIgnoreCase("help")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &d&lTeams &8&l&m-----" + "\n"
                        + "&d" + "\n"
                        + "&5/teams invite &7(player) - Invite a player to your team." + "\n"
                        + "&5/teams kick &7(player) - Kick a player out of your team." + "\n"
                        + "&5/teams setrole &7(player) (role) - Set a player his role." + "\n"
                        + "&5/teams ally &7(team) - Request an alliance with a team." + "\n"
                        + "&5/teams accept &7(team) - Accept an alliance request." + "\n"
                        + "&5/teams neutral &7(team) - Set a team to neutral." + "\n"
                        + "&5/teams create &7(team) (tag) - Create a team." + "\n"
                        + "&5/teams delete &7(team) - Delete a team." + "\n"
                        + "&5/teams set &7(player) (team) - Set a player in a team." + "\n"
                        + "\n"
                        + "&e"));
            }
            else if (args[0].equalsIgnoreCase("invite")){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    User user = TeamPlugin.instance.kgetUserManager().getUser(player);
                    if (player.hasPermission("teams.invite") || user.getPermissions().contains("teams.invite")){
                        if (user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name)){
                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_In_A_Team);
                            return true;
                        }
                        if (args.length > 1){
                            Player player2 = Bukkit.getPlayerExact(args[1]);
                            if (player2 != null){
                                User user2 = TeamPlugin.instance.kgetUserManager().getUser(player2);
                                if (!user.getTeam().equalsIgnoreCase(user2.getTeam()) && user2.getTeam() != null && user2.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name)){

                                    Team team = TeamPlugin.instance.kgetTeamManager().getTeam(user.getTeam().toLowerCase());

                                    TextComponent msg = new TextComponent(TeamPlugin.instance.kgetMessages().Invite_Button_Hover);
                                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TeamPlugin.instance.kgetMessages().Invite_Hover_Text).create()));
                                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team join " + user.getTeam().toLowerCase()));

                                    team.addInvite(player2);
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Succesfully_Invited_P
                                    .replace("%player%", player2.getName()));

                                    player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Invited_Message
                                            .replace("%team%", user.getTeam()));
                                    player2.spigot().sendMessage(msg);



                                }else{
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Other_Player);
                                }
                            }else{
                                player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Player_Not_Exists);
                            }
                        }else{
                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams invite (player)");
                        }
                    }else{
                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                    }
                }else{
                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Only players can execute this command.");
                }
            }
            else if (args[0].equalsIgnoreCase("join")){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    if (player.hasPermission("teams.join")){
                        if (args.length > 1){
                            String teamName = args[1].toLowerCase();
                            if (TeamPlugin.instance.kgetSystemMethods().CheckTeam(teamName)){
                                User user = TeamPlugin.instance.kgetUserManager().getUser(player);
                                if (user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name) && user.getTeam() != null){
                                    Team team = TeamPlugin.instance.kgetTeamManager().getTeam(teamName);
                                    if (team.getInvites().contains(player)){
                                        TeamMethods.SetPlayerTeam(player, teamName.toLowerCase());
                                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().UhaveBeenSetTeam
                                        .replace("%team%", team.getOfficialName()));
                                    }
                                }else{
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().CantBeInATeam);
                                }
                            }else{
                                player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Team_Not_Exists);
                            }
                        }else{
                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams join (team)");
                        }
                    }else{
                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                    }
                }else{
                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Only players can execute this command.");
                }
            }
            else if (args[0].equalsIgnoreCase("set")){
                if (sender.hasPermission("teams.set")){
                    if (args.length > 2){
                        Player player2 = Bukkit.getPlayerExact(args[1]);
                        if (player2 != null){
                            String teamArg = args[2];
                            if (TeamPlugin.instance.kgetSystemMethods().CheckTeam(teamArg.toLowerCase())){
                                Team team = TeamPlugin.instance.kgetTeamManager().getTeam(teamArg.toLowerCase());
                                TeamMethods.SetPlayerTeam(player2, teamArg.toLowerCase());
                                player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().UhaveBeenSetTeam.replace("%team%", team.getTeamPrefix()));
                                sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().HasBeenSuccesfullSet
                                .replace("%player%", player2.getName()).replace("%team%", team.getOfficialName()));
                            }else{
                                sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Team_Not_Exists);
                            }
                        }else{
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Player_Not_Exists);
                        }
                    }else{
                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams set (player) (team)");
                    }
                }else{
                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                }
            }
            else if (args[0].equalsIgnoreCase("kick")){
                    if (args.length > 1){
                        Player player2 = Bukkit.getPlayerExact(args[1]);
                        if (args[1].equalsIgnoreCase(sender.getName())){
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().U_Cant_Do_This_To_YourSelf);
                            return true;
                        }
                        if (player2 != null){
                            if (sender.hasPermission("teams.opkick") || !(sender instanceof Player)){

                                //BYPASS VOOR ALLE CHECKS.
                                TeamMethods.NoTeamSetPlayer(player2);
                                sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().PlayerTeamLess
                                        .replace("%player%", player2.getName()));
                                player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().KickedOutOfUTeam);
                            }else{

                                //ALS DE KICKER EEN SPELER IS WORD ER EEN CHECK UITGEVOERD VOOR SAME TEAM.
                                Player player = (Player) sender;
                                User user = TeamPlugin.instance.kgetUserManager().getUser(player);

                                if (user.getPermissions().contains("teams.kick") || player.hasPermission("teams.kick")){
                                    User user2 = TeamPlugin.instance.kgetUserManager().getUser(player2);

                                    if (!(user.getRoleWeight() > user2.getRoleWeight())){
                                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_Higher_Role);
                                        return true;
                                    }

                                    if (user.getTeam().equalsIgnoreCase(user2.getTeam())){
                                        TeamMethods.NoTeamSetPlayer(player2);
                                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().PlayerTeamLess
                                                .replace("%player%", player2.getName()));
                                        player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().KickedOutOfUTeam);

                                    }else{
                                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_Same_Team);
                                    }
                                }else{
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                                }

                            }
                        }else{
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Player_Not_Exists);
                        }
                    }else{
                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams kick (player)");
                    }
            }
            else if (args[0].equalsIgnoreCase("create")){
                if (sender.hasPermission("teams.create")){
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        User user = TeamPlugin.instance.kgetUserManager().getUser(player);
                        if (!user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name)){
                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().You_Already_In_A_Team);
                            return true;
                        }
                    }
                    if (args.length > 2){
                        String teamArg = args[1];
                        String teamPrefix = ChatColor.translateAlternateColorCodes('&', args[2]);
                        if (!(teamArg.length() >= 12)){
                            if (!TeamPlugin.instance.kgetSystemMethods().CheckTeam(teamArg.toLowerCase())){
                                if (args[2].length() <= 3){
                                    TeamMethods.CreateTeam(teamArg, teamPrefix.toUpperCase());
                                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().TeamHasBeenMade
                                            .replace("%team%", teamArg));
                                    if (sender instanceof Player){
                                        Player player = (Player) sender;

                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                TeamMethods.SetPlayerTeam(player, teamArg.toLowerCase());
                                                if (!TeamPlugin.instance.kgetRoles().checkRank(TeamPlugin.instance.kgetConfig().Leader_Rank))
                                                    System.out.println(SystemColors.TEXT_RED + "PLEASE FILL A CORRECT ROLE IN THE CONFIG! CHANGE THE LEADER ROLE PLEASE!!!!" + SystemColors.TEXT_RESET);
                                                TeamMethods.SetPlayerRank(player, TeamPlugin.instance.kgetConfig().Leader_Rank);
                                            }
                                        }.runTaskLaterAsynchronously(TeamPlugin.instance, 25);
                                    }
                                }else{
                                    if (args[2].length() <= 5){
                                        if (sender.hasPermission("teams.package")){
                                            TeamMethods.CreateTeam(teamArg, teamPrefix.toUpperCase());
                                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().TeamHasBeenMade
                                                    .replace("%team%", teamArg));
                                            if (sender instanceof Player){
                                                Player player = (Player) sender;

                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        TeamMethods.SetPlayerTeam(player, teamArg.toLowerCase());
                                                        if (!TeamPlugin.instance.kgetRoles().checkRank(TeamPlugin.instance.kgetConfig().Leader_Rank))
                                                            System.out.println(SystemColors.TEXT_RED + "PLEASE FILL A CORRECT ROLE IN THE CONFIG! CHANGE THE LEADER ROLE PLEASE!!!!" + SystemColors.TEXT_RESET);
                                                        TeamMethods.SetPlayerRank(player, TeamPlugin.instance.kgetConfig().Leader_Rank);
                                                    }
                                                }.runTaskLaterAsynchronously(TeamPlugin.instance, 30);
                                            }
                                        }else{
                                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().ToMuch_Chars_Perm);
                                        }
                                    }else{
                                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().ToMuch_Chars);
                                    }
                                }
                            }else{
                                sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().TeamAlreadyExists);
                            }
                        }else{
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().TeamName_Chars);
                        }
                    }else{
                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams create (team) (teamprefix)");
                    }
                }else{
                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                }
            }
            else if (args[0].equalsIgnoreCase("delete")){
                if (sender.hasPermission("teams.delete")){
                    if (args.length > 1){
                        String teamArg = args[1];
                        if (TeamPlugin.instance.kgetSystemMethods().CheckTeam(teamArg.toLowerCase())){
                            TeamMethods.DeleteTeam(teamArg.toLowerCase());
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().TeamHasBeenDeleted
                            .replace("%team%", teamArg));
                            for (Player loop_player : Bukkit.getOnlinePlayers()){
                                if (loop_player != null){
                                    User user = TeamPlugin.instance.kgetUserManager().getUser(loop_player);
                                    if (user.getTeam().equalsIgnoreCase(teamArg)) TeamMethods.NoTeamSetPlayer(loop_player);
                                }
                            }
                        }else{
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Team_Not_Exists);
                        }
                    }else{
                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams delete (team)");
                    }
                }else{
                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                }
            }
            else if (args[0].equalsIgnoreCase("setrole")){
                    if (args.length > 2){
                        Player player2 = Bukkit.getPlayerExact(args[1]);
                        String rankarg = args[2];
                        if (player2 != null){
                            if (sender.hasPermission("teams.opsetrole")){
                                if (TeamPlugin.instance.kgetRoles().checkRank(rankarg)){

                                    TeamMethods.SetPlayerRank(player2, rankarg);

                                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Succesfull_Changed_Some_Role
                                    .replace("%player%", player2.getName()).replace("%role%", rankarg));
                                    player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Ur_Role_Changed_Succesfully
                                    .replace("%role%", rankarg));
                                }else{
                                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Role_Does_Not_Exist);
                                }
                            }else{
                                if (sender instanceof Player){
                                    Player player = (Player) sender;

                                    if (args[1].equalsIgnoreCase(sender.getName())){
                                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().U_Cant_Do_This_To_YourSelf);
                                        return true;
                                    }

                                    User user = TeamPlugin.instance.kgetUserManager().getUser(player);
                                    if (user.getPermissions().contains("teams.setrole") || player.hasPermission("teams.setrole")){
                                        User user2 = TeamPlugin.instance.kgetUserManager().getUser(player2);

                                        if (!(user.getRoleWeight() > user2.getRoleWeight())){
                                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_Higher_Role);
                                            return true;
                                        }

                                        if (user.getTeam().equalsIgnoreCase(user2.getTeam())){

                                            if (TeamPlugin.instance.kgetRoles().checkRank(rankarg)){

                                                if (!rankarg.equalsIgnoreCase(TeamPlugin.instance.kgetConfig().Leader_Rank)){

                                                    TeamMethods.SetPlayerRank(player2, rankarg);
                                                    TeamMethods.SetPlayerRank(player, TeamPlugin.instance.kgetConfig().CoLeader_Rank);

                                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Ur_Member_Now
                                                    .replace("%player%", player2.getName()));

                                                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Succesfull_Changed_Some_Role
                                                            .replace("%player%", player2.getName()).replace("%role%", rankarg));
                                                    player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Ur_Role_Changed_Succesfully
                                                            .replace("%role%", rankarg));

                                                    return true;

                                                }

                                                TeamMethods.SetPlayerRank(player2, rankarg);

                                                sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Succesfull_Changed_Some_Role
                                                        .replace("%player%", player2.getName()).replace("%role%", rankarg));
                                                player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Ur_Role_Changed_Succesfully
                                                        .replace("%role%", rankarg));

                                            }else{

                                                player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Role_Does_Not_Exist);
                                            }

                                        }else{
                                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_Same_Team);
                                        }
                                    }else{
                                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                                    }
                                }
                            }
                        }else{
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Player_Not_Exists);
                        }
                    }else{
                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams setrole (player) (role)");
                    }
            }

            else if (args[0].equalsIgnoreCase("ally")){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    User user = TeamPlugin.instance.kgetUserManager().getUser(player);

                    if (user.hasPermission("teams.ally") || player.hasPermission("teams.ally")){
                        if (!user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name) && user.getTeam() != null){
                            if (args.length > 1){

                                String teamArg = args[1];

                                if (user.getTeam().equalsIgnoreCase(teamArg)){
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Cannot_Own_Team);
                                    return true;
                                }

                                if (TeamPlugin.instance.kgetSystemMethods().CheckTeam(teamArg.toLowerCase())){

                                    Team teamPlayer = TeamPlugin.instance.kgetTeamManager().getTeam(user.getTeam().toLowerCase());
                                    Team teamArgt = TeamPlugin.instance.kgetTeamManager().getTeam(teamArg.toLowerCase());

                                    if (teamPlayer.getAllies().contains(teamArg.toLowerCase())){
                                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Already_An_Ally);
                                        return true;
                                    }

                                    if (teamPlayer.getAllyVerzoeken().contains(teamArg.toLowerCase())){
                                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Ally_Already_Sent);
                                        return true;
                                    }

                                    //IF ALL THE IF'S ARE PASSED (THE CHECKS)

                                    teamPlayer.addAnAllyVerzoek(teamArg.toLowerCase());
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Succesfully_Requested_Ally
                                    .replace("%team%", teamArgt.getOfficialName()));

                                    TextComponent msg = new TextComponent(TeamPlugin.instance.kgetMessages().Ally_Request_Message
                                            .replace("%team%", teamPlayer.getOfficialName()));
                                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TeamPlugin.instance.kgetMessages().Ally_Request_Hover).create()));
                                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team accept " + teamPlayer.getTeamName().toLowerCase()));

                                    for (Player lp : Bukkit.getOnlinePlayers()){
                                        User userLP = TeamPlugin.instance.kgetUserManager().getUser(lp);
                                        if (userLP.getTeam().equalsIgnoreCase(teamArg)){
                                            if (userLP.hasPermission("teams.leader") || lp.hasPermission("teams.leader")){
                                                lp.spigot().sendMessage(msg);
                                            }
                                        }
                                    }


                                }else{
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Team_Not_Exists);
                                }

                            }else{
                                player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams ally (team)");
                            }

                        }else{
                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_In_A_Team);
                        }
                    }else{
                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                    }

                }else{
                    sender.sendMessage("You can not execute this command through the console.");
                }

            }

            else if (args[0].equalsIgnoreCase("accept")){
                if (sender instanceof Player){

                    Player player = (Player) sender;
                    User user = TeamPlugin.instance.kgetUserManager().getUser(player);

                    if (player.hasPermission("teams.accept") || user.hasPermission("teams.accept")){

                        if (!user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name) && user.getTeam() != null){

                            if (args.length > 1){

                                String teamArg = args[1];

                                if (user.getTeam().equalsIgnoreCase(teamArg)){
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Cannot_Own_Team);
                                    return true;
                                }

                                if (TeamPlugin.instance.kgetSystemMethods().CheckTeam(teamArg.toLowerCase())){

                                    Team teamPlayer = TeamPlugin.instance.kgetTeamManager().getTeam(user.getTeam().toLowerCase());
                                    Team teamArgt = TeamPlugin.instance.kgetTeamManager().getTeam(teamArg.toLowerCase());

                                    if (teamArgt.getAllyVerzoeken().contains(teamPlayer.getTeamName().toLowerCase())){

                                        teamArgt.removeAllyVerzoek(teamPlayer.getTeamName().toLowerCase());

                                        TeamMethods.AddAnAlly(teamPlayer.getTeamName().toLowerCase(), teamArgt.getTeamName().toLowerCase());


                                    }else{

                                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Not_Requested_Alliance);
                                    }

                                }else{

                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Team_Not_Exists);
                                }
                            }else{

                                player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams accept (team)");
                            }
                        }else{

                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_In_A_Team);
                        }

                    }else{

                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                    }

                }else{
                    sender.sendMessage("You can not execute this command through the console.");
                }

            }

            else if (args[0].equalsIgnoreCase("neutral")){
                if (sender instanceof Player){

                    Player player = (Player) sender;
                    User user = TeamPlugin.instance.kgetUserManager().getUser(player);

                    if (player.hasPermission("teams.neutral") || user.hasPermission("teams.neutral")){

                        if (!user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name) && user.getTeam() != null){

                            if (args.length > 1){

                                String teamArg = args[1];

                                if (user.getTeam().equalsIgnoreCase(teamArg)){
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Cannot_Own_Team);
                                    return true;
                                }

                                if (TeamPlugin.instance.kgetSystemMethods().CheckTeam(teamArg.toLowerCase())) {

                                    Team teamPlayer = TeamPlugin.instance.kgetTeamManager().getTeam(user.getTeam().toLowerCase());
                                    Team teamArgt = TeamPlugin.instance.kgetTeamManager().getTeam(teamArg.toLowerCase());

                                    if (teamPlayer.getAllies().contains(teamArgt.getTeamName().toLowerCase())){

                                        teamPlayer.removeAlly(teamArgt.getTeamName().toLowerCase());
                                        teamArgt.removeAlly(teamPlayer.getTeamName().toLowerCase());

                                        TeamMethods.RemoveAnAlly(teamPlayer.getTeamName().toLowerCase(), teamArgt.getTeamName().toLowerCase());

                                    }


                                }else{

                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Team_Not_Exists);
                                }

                            }else{

                                player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams neutral (team)");
                            }

                        }else{

                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_In_A_Team);
                        }

                    }else{

                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                    }

                }else{

                    sender.sendMessage("You can not execute this command through the console.");
                }
            }

            else if (args[0].equalsIgnoreCase("leave")){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    User user = TeamPlugin.instance.kgetUserManager().getUser(player);
                    if (player.hasPermission("teams.leave") || user.hasPermission("teams.leave")){
                        if (user.getTeam() != null && !user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name)){

                            TeamMethods.NoTeamSetPlayer(player);

                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Succes_Leaved_Team);

                            for (Player wp : Bukkit.getOnlinePlayers()){
                                User uWP = TeamPlugin.instance.kgetUserManager().getUser(wp);
                                if (uWP.getTeam().equalsIgnoreCase(user.getTeam()))
                                    wp.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Member_Leaved);
                            }

                        }else{

                            player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Have_To_Be_In_A_Team);
                        }

                    }else{

                        player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                    }

                }else{

                    sender.sendMessage("You can not execute this command through the console.");
                }

            }

            else if (args[0].equalsIgnoreCase("test")){
                if (sender instanceof Player){

                    Player player = (Player) sender;
                    User user = TeamPlugin.instance.kgetUserManager().getUser(player);

                    player.sendMessage("Rank: " + user.getRole());
                    player.sendMessage("RankPrefix: " + user.getRolePrefix());
                    player.sendMessage("RankDisplay: " + user.getRoleDisplay());
                    player.sendMessage("RankWeight: " + user.getRoleWeight());

                }
            }else{

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &d&lTeams &8&l&m-----" + "\n"
                        + "&d" + "\n"
                        + "&5/teams invite &7(player) - Invite a player to your team." + "\n"
                        + "&5/teams kick &7(player) - Kick a player out of your team." + "\n"
                        + "&5/teams setrole &7(player) (role) - Set a player his role." + "\n"
                        + "&5/teams ally &7(team) - Request an alliance with a team." + "\n"
                        + "&5/teams accept &7(team) - Accept an alliance request." + "\n"
                        + "&5/teams neutral &7(team) - Set a team to neutral." + "\n"
                        + "&5/teams create &7(team) (tag) - Create a team." + "\n"
                        + "&5/teams delete &7(team) - Delete a team." + "\n"
                        + "&5/teams set &7(player) (team) - Set a player in a team." + "\n"
                        + "\n"
                        + "&e"));
            }


        }

        return false;
    }
}
