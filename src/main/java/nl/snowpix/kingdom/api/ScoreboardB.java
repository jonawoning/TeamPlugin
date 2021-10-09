package nl.snowpix.kingdom.api;

import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.tab.NametagManager;
import nl.snowpix.kingdom.war.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;

public class ScoreboardB {

    public static void EnableSB(){
        new BukkitRunnable() {

            @Override
            public void run() {
                int totalSecs = Kingdom.instance.kGetConfig().stimer;
                if (totalSecs < 0) totalSecs = 0;
                int hours = totalSecs / 3600;
                int minutes = totalSecs % 3600 / 60;
                int seconds = totalSecs % 60;

                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                //

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (Kingdom.instance.kGetConfig().state.equals(State.Status.Normal)) {

                        //NORMAL SCOREBOARD

                        List<String> linesofboard = Kingdom.instance.c.getStringList("n_Scoreboard_Lines");
                        Collections.reverse(linesofboard);
                        int i = 0;

                        for (String string : linesofboard) {

                            String text = ChatColor.translateAlternateColorCodes('&', string);
                            i++;
                            Scoreboard.update(player, String.valueOf(i), text);
                        }
                        //
                    }

                    else if (Kingdom.instance.kGetConfig().state.equals(State.Status.WorldWar)) {

                        //WORLDWAR SCOREBOARD

                        List<String> linesofboard = Kingdom.instance.c.getStringList("w_Scoreboard_Lines");
                        Collections.reverse(linesofboard);
                        int i = 0;
                        int p = 0;

                        for (String string : linesofboard) {

                            String text = ChatColor.translateAlternateColorCodes('&', string);
                            i++;

                            if (string.contains("%kingdoms%")) {
                                for (String kingdoms : Kingdom.instance.kgetKingdomsMethods().Kingdoms) {
                                    Kingdoms kingdomsm = Kingdom.instance.kgetKingdomManager().getKingdom(kingdoms);
                                    p = i;
                                    if (p >= (Kingdom.instance.kgetKingdomsMethods().Kingdoms.size() + i)) {
                                        break;
                                    }
                                    Scoreboard.update(player, String.valueOf(i), kingdomsm.getKingdomDisplay() + Kingdom.instance.kGetConfig().Kingdom_Format_War
                                            .replace("%kills%", Kingdom.instance.kGetConfig().War_Kills_PerKD.containsKey(kingdoms.toLowerCase()) ?
                                                    String.valueOf(Kingdom.instance.kGetConfig().War_Kills_PerKD.get(kingdoms.toLowerCase())) : String.valueOf(0)));
                                    p++;
                                    i++;
                                }

                            } else {
                                Scoreboard.update(player, String.valueOf(i), text.replace("%timer%", timeString));
                            }

                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Kingdom.instance, 0L, 20L);
    }

    public static void setScoreBoard(Player player){

        if (Kingdom.instance.kGetConfig().state.equals(State.Status.Normal)){

            Scoreboard scoreboard = new Scoreboard(Kingdom.instance.kGetConfig().n_Scoreboard_Title, player);

            List<String> linesofboard = Kingdom.instance.c.getStringList("n_Scoreboard_Lines");
            Collections.reverse(linesofboard);
            int i = 0;

            for (String string : linesofboard){
                string = ChatColor.translateAlternateColorCodes('&', string);
                i++;

                scoreboard.add(String.valueOf(i), string, player);
            }

            scoreboard.apply();
        }
        else if (Kingdom.instance.kGetConfig().state.equals(State.Status.WorldWar)){

            Scoreboard scoreboard = new Scoreboard(Kingdom.instance.kGetConfig().n_Scoreboard_Title, player);

            List<String> linesofboard = Kingdom.instance.c.getStringList("w_Scoreboard_Lines");
            Collections.reverse(linesofboard);
            int i = 0;
            int p = 0;

            for (String string : linesofboard) {

                String text = ChatColor.translateAlternateColorCodes('&', string);
                i++;

                if (string.contains("%kingdoms%")) {
                    for (String kingdoms : Kingdom.instance.kgetKingdomsMethods().Kingdoms) {
                        Kingdoms kingdomsm = Kingdom.instance.kgetKingdomManager().getKingdom(kingdoms);
                        p = i;
                        if (p >= (Kingdom.instance.kgetKingdomsMethods().Kingdoms.size() + i)) {
                            break;
                        }
                        scoreboard.add(String.valueOf(i), kingdomsm.getKingdomDisplay() + Kingdom.instance.kGetConfig().Kingdom_Format_War
                                .replace("%kills%", String.valueOf(kingdomsm.getW_Kills())), player);
                        p++;
                        i++;
                    }

                } else {
                    scoreboard.add(String.valueOf(i), text, player);
                }

            }

            scoreboard.apply();

        }


    }

    public static void CheckIfSB(){
        if (Kingdom.instance.kGetConfig().Use_Scoreboard){
            EnableSB();
        }
    }

    public static void UpdateNameTags(){
        new BukkitRunnable() {

            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()){

                    NametagManager.getInstance().reset(player.getName());

                    User user1 = Kingdom.instance.kgetUserManager().getUser(player);
                    NametagManager.getInstance().setNametag(player.getName(), (user1.getTab() != null && !user1.getTab().equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', user1.getTab()) : ""), (user1.getTabSuffix() != null && !user1.getTabSuffix().equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', user1.getTabSuffix()) : null));

                }

            }
        }.runTaskTimerAsynchronously(Kingdom.instance, 0L, 55L);
    }

}
