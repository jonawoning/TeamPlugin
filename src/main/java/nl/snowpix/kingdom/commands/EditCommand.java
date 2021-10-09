package nl.snowpix.kingdom.commands;

import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.methods.EditListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EditCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("edit.admin")){
            if (args.length == 0){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &b&lEditor &8&l&m-----" + "\n"
                        + "&d" + "\n"
                        + "&9/edit prefix &7(kingdom) (prefix) &7- Change a prefix of a kingdom." + "\n"
                        + "&9/edit display &7(kingdom) (display) &7- Change a display of a kingdom." + "\n"
                        + "&9/edit tab &7(kingdom) (tab) &7- Change a tab prefix of a kingdom." + "\n"
                        + "&9/edit soort &7(kingdom) (soort) &7- Change a kind/creature of a kingdom." + "\n"
                        + "&9/edit help &7- Lookup the help page of the edit command." + "\n"
                        + "\n"
                        + "&7The permission &aedit.admin &7is needed to use the commands."
                        + "&e"));
            }else{

                if (args[0].equalsIgnoreCase("help")){

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m-----&7 &b&lEditor &8&l&m-----" + "\n"
                            + "&d" + "\n"
                            + "&9/edit prefix &7(kingdom) (prefix) &7- Change a prefix of a kingdom." + "\n"
                            + "&9/edit display &7(kingdom) (display) &7- Change a display of a kingdom." + "\n"
                            + "&9/edit tab &7(kingdom) (tab) &7- Change a tab prefix of a kingdom." + "\n"
                            + "&9/edit soort &7(kingdom) (soort) &7- Change a kind/creature of a kingdom." + "\n"
                            + "&9/edit help &7- Lookup the help page of the edit command." + "\n"
                            + "\n"
                            + "&7The permission &aedit.admin &7is needed to use the commands."
                            + "&e"));
                }
                else if (args[0].equalsIgnoreCase("prefix")){
                    if (args.length > 2){
                        String kdnaam = args[1];
                        if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kdnaam)){
                            final String prefix;
                            if (args[3] == null){
                                prefix = args[2];
                            }else{
                                prefix = args[2] + " " + args[3];
                            }
                            EditListener.EditPrefix(kdnaam.toLowerCase(), prefix);
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Prefix_Succesfull_Edit
                                    .replace("%kingdom%", kdnaam).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', prefix)));
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', "&cUse: /edit prefix (kingdom) (prefix)"));
                    }
                }
                else if (args[0].equalsIgnoreCase("display")){
                    if (args.length > 2){
                        String kdnaam = args[1];
                        if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kdnaam)){
                            final String display;
                            if (args[3] == null){
                                display = args[2];
                            }else{
                                display = args[2] + " " + args[3];
                            }
                            EditListener.EditDisplay(kdnaam.toLowerCase(), display);
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Display_Succesfull_Edit
                                    .replace("%kingdom%", kdnaam).replace("%display%", ChatColor.translateAlternateColorCodes('&', display)));
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', "&cUse: /edit display (kingdom) (display)"));
                    }
                }
                else if (args[0].equalsIgnoreCase("tab")){
                    if (args.length > 2){
                        String kdnaam = args[1];
                        if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kdnaam)){
                            final String tab;
                            if (args[3] == null){
                                tab = args[2];
                            }else{
                                tab = args[2] + " " + args[3];
                            }
                            EditListener.EditTab(kdnaam.toLowerCase(), tab);
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Tab_Succesfull_Edit
                                    .replace("%kingdom%", kdnaam).replace("%tab%", ChatColor.translateAlternateColorCodes('&', tab)));
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', "&cUse: /edit tab (kingdom) (tab)"));
                    }
                }
                else if (args[0].equalsIgnoreCase("soort")){
                    if (args.length > 2){
                        String kdnaam = args[1];
                        if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kdnaam)){
                            final String soort;
                            if (args[3] == null){
                                soort = args[2];
                            }else{
                                soort = args[2] + " " + args[3];
                            }
                            EditListener.EditSoort(kdnaam.toLowerCase(), soort);
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Soort_Succesfull_Edit
                                    .replace("%kingdom%", kdnaam).replace("%soort%", ChatColor.translateAlternateColorCodes('&', soort)));
                        }else{
                            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exists);
                        }
                    }else{
                        sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + ChatColor.translateAlternateColorCodes('&', "&cUse: /edit soort (kingdom) (soort)"));
                    }
                }
            }
        }else{
            sender.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().No_Permission);
        }
        return false;
    }
}
