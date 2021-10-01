package nl.snowpix.teamplugin.commands;

import nl.snowpix.teamplugin.TeamPlugin;
import nl.snowpix.teamplugin.data.TeamP.Team;
import nl.snowpix.teamplugin.data.UserP.User;
import nl.snowpix.teamplugin.methods.TeamMethods;
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
                        + "&5/teams create &7(team) (tag) - Create a team." + "\n"
                        + "&5/teams delete &7(team) - Delete a team." + "\n"
                        + "&5/teams set &7(player) (team) - Set a player in a team." + "\n"
                        + "\n"
                        + "&e"));
            }
            else if (args[0].equalsIgnoreCase("invite")){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    if (player.hasPermission("teams.invite")){
                        if (args.length > 1){
                            Player player2 = Bukkit.getPlayerExact(args[1]);
                            if (player2 != null){
                                User user = TeamPlugin.instance.kgetUserManager().getUser(player);
                                User user2 = TeamPlugin.instance.kgetUserManager().getUser(player2);
                                if (!user.getTeam().equalsIgnoreCase(user2.getTeam()) && user2.getTeam() != null && !user2.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name)){
                                    Team team = TeamPlugin.instance.kgetTeamManager().getTeam(user.getTeam().toLowerCase());
                                    team.addInvite(player2);
                                    player.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Succesfully_Invited_P
                                    .replace("%player%", player2.getName()));
                                    player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Invited_Message
                                    .replace("%team%", user.getTeam()));
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
                                if (!user.getTeam().equalsIgnoreCase(TeamPlugin.instance.kgetConfig().NoTeam_Name) && user.getTeam() != null){
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
                if (sender.hasPermission("teams.kick")){
                    if (args.length > 1){
                        Player player2 = Bukkit.getPlayerExact(args[1]);
                        if (player2 != null){
                            TeamMethods.NoTeamSetPlayer(player2);
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().PlayerTeamLess
                            .replace("%player%", player2.getName()));
                            player2.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().KickedOutOfUTeam);
                        }else{
                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().Player_Not_Exists);
                        }
                    }else{
                        sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + "Use: /teams kick (player)");
                    }
                }else{
                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().No_Permission);
                }
            }
            else if (args[0].equalsIgnoreCase("create")){
                if (sender.hasPermission("teams.create")){
                    if (args.length > 2){
                        String teamArg = args[1];
                        String teamPrefix = ChatColor.translateAlternateColorCodes('&', args[2]);
                        if (!(teamArg.length() >= 12)){
                            if (!TeamPlugin.instance.kgetSystemMethods().CheckTeam(teamArg.toLowerCase())){
                                if (args[2].length() <= 3){
                                    TeamMethods.CreateTeam(teamArg, teamPrefix);
                                    sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().TeamHasBeenMade
                                            .replace("%team%", teamArg));
                                    if (sender instanceof Player){
                                        Player player = (Player) sender;

                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                TeamMethods.SetPlayerTeam(player, teamArg.toLowerCase());
                                            }
                                        }.runTaskLaterAsynchronously(TeamPlugin.instance, 35);
                                    }
                                }else{
                                    if (args[2].length() <= 5){
                                        if (sender.hasPermission("teams.package")){
                                            TeamMethods.CreateTeam(teamArg, teamPrefix);
                                            sender.sendMessage(TeamPlugin.instance.kgetConfig().Prefix + TeamPlugin.instance.kgetMessages().TeamHasBeenMade
                                                    .replace("%team%", teamArg));
                                            if (sender instanceof Player){
                                                Player player = (Player) sender;

                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        TeamMethods.SetPlayerTeam(player, teamArg.toLowerCase());
                                                    }
                                                }.runTaskLaterAsynchronously(TeamPlugin.instance, 35);
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


        }

        return false;
    }
}
