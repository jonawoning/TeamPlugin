package nl.snowpix.kingdom.methods;

import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerData {

    public static void CheckPlayerData(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata", player.getUniqueId() + ".yml"));
            final File playerdata = new File("plugins//Kingdom//playerdata//" + player.getUniqueId() + ".yml");
            final String kingdom = String.valueOf(cfg_playerdata.get("Kingdom"));
            final String player_rank = String.valueOf(cfg_playerdata.get("Rank"));
            if (!playerdata.exists()) {

                //LOAD FOR THE FIRST TIME

                cfg_playerdata.set("Kingdom", Kingdom.instance.kGetConfig().NoKingdom_Name);
                cfg_playerdata.set("Kingdom_Prefix", Kingdom.instance.kGetConfig().NoKingdom_Prefix);
                cfg_playerdata.set("Kingdom_Display", Kingdom.instance.kGetConfig().NoKingdom_Display);
                cfg_playerdata.set("Rank", Kingdom.instance.kGetConfig().NoKingdom_Rank);
                cfg_playerdata.set("Rank_Prefix", Kingdom.instance.kGetConfig().NoKingdom_Rank_Prefix);
                cfg_playerdata.set("Rank_Display", Kingdom.instance.kGetConfig().NoKingdom_Rank_Display);
                cfg_playerdata.set("Rank_Weight", 0);
                cfg_playerdata.set("Tab", Kingdom.instance.kGetConfig().NoKingdom_Tab);
                cfg_playerdata.set("Soort", Kingdom.instance.kGetConfig().NoKingdom_Soort);
                cfg_playerdata.set("Points", 0);

                try {
                    cfg_playerdata.save(playerdata);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                if (!kingdom.equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)) {

                    //PLAYERDATA IS BEING LOADED FOR PLAYERS IN A KINGDOM.

                    final YamlConfiguration cfg_ranksfile = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//ranks", "ranks.yml"));

                    if (Kingdom.instance.kgetKingdomsMethods().CheckKingdom(kingdom.toLowerCase())) {

                        Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom.toLowerCase());

                        final String rankprefix = String.valueOf(cfg_ranksfile.get("Ranks." + player_rank + ".Prefix"));
                        final String rankdisplay = String.valueOf(cfg_ranksfile.get("Ranks." + player_rank + ".Display"));
                        final String rankweight = String.valueOf(cfg_ranksfile.get("Ranks." + player_rank + ".Weight"));
                        cfg_playerdata.set("Kingdom_Prefix", kingdoms.getKingdomPrefix());
                        cfg_playerdata.set("Kingdom_Display", kingdoms.getKingdomDisplay());
                        cfg_playerdata.set("Rank_Prefix", rankprefix);
                        cfg_playerdata.set("Rank_Display", rankdisplay);
                        cfg_playerdata.set("Rank_Weight", rankweight);
                        if (!Kingdom.instance.kgetRanks().ALL_RANKS.contains(player_rank)) {
                            cfg_playerdata.set("Rank", Kingdom.instance.kGetConfig().Member_Rank);
                            cfg_playerdata.set("Rank_Prefix", String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + Kingdom.instance.kGetConfig().Member_Rank + ".Prefix")));
                            cfg_playerdata.set("Rank_Display", String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + Kingdom.instance.kGetConfig().Member_Rank + ".Display")));
                            cfg_playerdata.set("Rank_Weight", String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + Kingdom.instance.kGetConfig().Member_Rank + ".Weight")));
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Rank_Deleted_Join);
                        }
                        cfg_playerdata.set("Tab", kingdoms.getKingdomTab());
                        cfg_playerdata.set("Soort", kingdoms.getSoort());
                        try {
                            cfg_playerdata.save(playerdata);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {

                        //Kingdom has been deleted so the player is set to default.

                        ExtraMethods.MakePlayerNO_KD(player);

                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Kingdom_Dont_Exist_Anymore);

                    }
                } else {


                    cfg_playerdata.set("Kingdom", Kingdom.instance.kGetConfig().NoKingdom_Name);
                    cfg_playerdata.set("Kingdom_Prefix", Kingdom.instance.kGetConfig().NoKingdom_Prefix);
                    cfg_playerdata.set("Kingdom_Display", Kingdom.instance.kGetConfig().NoKingdom_Display);
                    cfg_playerdata.set("Rank", Kingdom.instance.kGetConfig().NoKingdom_Rank);
                    cfg_playerdata.set("Rank_Prefix", Kingdom.instance.kGetConfig().NoKingdom_Rank_Prefix);
                    cfg_playerdata.set("Rank_Display", Kingdom.instance.kGetConfig().NoKingdom_Rank_Display);
                    cfg_playerdata.set("Rank_Weight", 0);
                    cfg_playerdata.set("Tab", Kingdom.instance.kGetConfig().NoKingdom_Tab);
                    cfg_playerdata.set("Soort", Kingdom.instance.kGetConfig().NoKingdom_Soort);
                    try {
                        cfg_playerdata.save(playerdata);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        });

    }
}
