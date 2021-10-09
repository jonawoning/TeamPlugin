package nl.snowpix.kingdom.commands;

import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Points implements CommandExecutor {

    public static ArrayList<Player> pointsbypass = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("points.use")){
            if (args.length > 0){
                if (args[0].equalsIgnoreCase("help")){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &e&lPoints &8&l&m-----" + "\n"
                            + "&d" + "\n"
                            + "&e/points give &7(player) (points) &7- Give points to a player. > points.give" + "\n"
                            + "&e/points set &7(player) (points) &7- Set the points of a player to a number. > points.set" + "\n"
                            + "&e/points remove &7(player) (points) &7- Remove points from a player. > points.remove" + "\n"
                            + "&e/points bypass &7- Bypass the points/influence system. > points.bypass"
                            + "&e"));
                }else if (args[0].equalsIgnoreCase("give")){
                    if (sender.hasPermission("points.give")){
                        if (args.length > 2 && isInt(args[2])){
                            Player target = Bukkit.getPlayerExact(args[1]);
                            int pointstogive = Integer.parseInt(args[2]);
                            if (target != null){
                                final YamlConfiguration cfg_targetdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata", target.getUniqueId() + ".yml"));
                                final File targetdata = new File("plugins//Kingdom//playerdata//" + target.getUniqueId() + ".yml");
                                int Momentpoints = (int) cfg_targetdata.get("Points");
                                cfg_targetdata.set("Points", Momentpoints + pointstogive);

                                try {
                                    cfg_targetdata.save(targetdata);
                                } catch (IOException e){
                                    e.printStackTrace();
                                }

                                Kingdom.instance.kgetUserManager().removeUser(target);
                                Kingdom.instance.kgetUserManager().addUser(target);

                                sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Sender_Given.replace("%target%", target.getName()).replace("%points%", String.valueOf(pointstogive)));
                                target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Player_Given.replace("%points%", String.valueOf(pointstogive)).replace("%sender%", sender.getName()));

                            }else{
                                sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Incorrect_Player);
                            }
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /points give (player) (points)");
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }else if (args[0].equalsIgnoreCase("set")){
                    if (sender.hasPermission("points.set")){
                        if (args.length > 2 && isInt(args[2])){
                            Player target = Bukkit.getPlayerExact(args[1]);
                            int pointstoset = Integer.parseInt(args[2]);
                            if (target != null){
                                final YamlConfiguration cfg_targetdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata", target.getUniqueId() + ".yml"));
                                final File targetdata = new File("plugins//Kingdom//playerdata//" + target.getUniqueId() + ".yml");
                                cfg_targetdata.set("Points", pointstoset);

                                try {
                                    cfg_targetdata.save(targetdata);
                                } catch (IOException e){
                                    e.printStackTrace();
                                }

                                Kingdom.instance.kgetUserManager().removeUser(target);
                                Kingdom.instance.kgetUserManager().addUser(target);

                                sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Sender_Set.replace("%target%", target.getName()).replace("%points%", String.valueOf(pointstoset)));
                                target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Player_Set.replace("%points%", String.valueOf(pointstoset)).replace("%sender%", sender.getName()));
                            }else{
                                sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Incorrect_Player);
                            }
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /points set (player) (points)");
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }else if (args[0].equalsIgnoreCase("remove")){
                    if (sender.hasPermission("points.remove")){
                        if (args.length > 2 && isInt(args[2])){
                            Player target = Bukkit.getPlayerExact(args[1]);
                            int pointstoremove = Integer.parseInt(args[2]);
                            if (target != null){
                                final YamlConfiguration cfg_targetdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata", target.getUniqueId() + ".yml"));
                                final File targetdata = new File("plugins//Kingdom//playerdata//" + target.getUniqueId() + ".yml");
                                int Momentpoints = (int) cfg_targetdata.get("Points");
                                cfg_targetdata.set("Points", Momentpoints - pointstoremove);

                                try {
                                    cfg_targetdata.save(targetdata);
                                } catch (IOException e){
                                    e.printStackTrace();
                                }

                                Kingdom.instance.kgetUserManager().removeUser(target);
                                Kingdom.instance.kgetUserManager().addUser(target);

                                sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Sender_Remove.replace("%target%", target.getName()).replace("%points%", String.valueOf(pointstoremove)));
                                target.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Player_Remove.replace("%points%", String.valueOf(pointstoremove)).replace("%sender%", sender.getName()));
                            }else{
                                sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Incorrect_Player);
                            }
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "Usage: /points remove (player) (points)");
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                    }
                }else if (args[0].equalsIgnoreCase("bypass")){
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        if (player.hasPermission("points.bypass")){
                            if (pointsbypass.contains(player)){
                                pointsbypass.remove(player);
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', "Points Bypass > &cOff"));
                            }else{
                                pointsbypass.add(player);
                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', "Points Bypass > &aOn"));
                            }
                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
                        }
                    }else{
                        System.out.println("This can only be done by players.");
                    }
                }else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &e&lPoints &8&l&m-----" + "\n"
                            + "&d" + "\n"
                            + "&e/points give &7(player) (points) &7- Give points to a player. > points.give" + "\n"
                            + "&e/points set &7(player) (points) &7- Set the points of a player to a number. > points.set" + "\n"
                            + "&e/points remove &7(player) (points) &7- Remove points from a player. > points.remove" + "\n"
                            + "&e/points bypass &7- Toggle for bypassing the points/influence system. > points.bypass"
                            + "&e"));
                }
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &e&lPoints &8&l&m-----" + "\n"
                        + "&d" + "\n"
                        + "&e/points give &7(player) (points) &7- Give points to a player. > points.give" + "\n"
                        + "&e/points set &7(player) (points) &7- Set the points of a player to a number. > points.set" + "\n"
                        + "&e/points remove &7(player) (points) &7- Remove points from a player. > points.remove" + "\n"
                        + "&e/points bypass &7- Bypass the points/influence system. > points.bypass"
                        + "&e"));
            }
        }else{
            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
        }
        return false;
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
