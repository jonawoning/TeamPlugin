package nl.snowpix.kingdom.methods;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.clip.placeholderapi.PlaceholderAPI;
import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.tab.NametagManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtraMethods {


    public static String getPlayerKingdo(Player player){
        final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return String.valueOf(cfg_playerdata.get("Kingdom"));
    }


    public static String getPlayerPrefix(Player player){
        final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(cfg_playerdata.get("Kingdom_Prefix")));

    }

    public static String getPlayerDisplay(Player player){
        final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(cfg_playerdata.get("Kingdom_Display")));
    }

    public static String getPlayerRankPrefix(Player player){
        final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(cfg_playerdata.get("Rank_Prefix")));
    }

    public static String getPlayerRankDisplay(Player player){
        final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(cfg_playerdata.get("Rank_Display")));
    }

    public static String getPlayerTab(Player player){
        final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(cfg_playerdata.get("Tab")));
    }

    public static String getPlayerRank(Player player){
        final YamlConfiguration cfg_messages = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return String.valueOf(cfg_messages.get("Rank"));
    }

    public static String getPlayerSoort(Player player){
        final YamlConfiguration cfg_messages = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return String.valueOf(cfg_messages.get("Soort"));
    }

    public static int getPlayerPoints(Player player){
        final YamlConfiguration cfg_messages = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return (int) cfg_messages.get("Points");
    }

    public static int getPlayerRankWeigh(Player player){
        final YamlConfiguration cfg_messages = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
        return cfg_messages.getInt("Rank_Weight");
    }

    public static String cmessage(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getaKingdomPrefix(String kingdom){
        final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom + ".yml"));
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(cfg_kingdomdata.get("Prefix")));
    }

    public static String getaKingdomDispla(String kingdom){
        final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom + ".yml"));
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(cfg_kingdomdata.get("Display")));
    }

    public static String getaKingdomName(String kingdom){
        final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom + ".yml"));
        return ChatColor.translateAlternateColorCodes('&', String.valueOf(cfg_kingdomdata.get("Name")));
    }

    public static int getaKingdomWKill(String kingdom){
        final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom + ".yml"));
        return (int) cfg_kingdomdata.get("W_Kills");
    }

    public static int getaKingdomKill(String kingdom){
        final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom + ".yml"));
        return (int) cfg_kingdomdata.get("Kills");
    }

    public static void RemovePlayerPoints(Player player, int pointstoremove){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_targetdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata", player.getUniqueId() + ".yml"));
            final File targetdata = new File("plugins//Kingdom//playerdata//" + player.getUniqueId() + ".yml");
            int momentpoints = (int) cfg_targetdata.get("Points");
            cfg_targetdata.set("Points", momentpoints - pointstoremove);

            try {
                cfg_targetdata.save(targetdata);
            } catch (IOException e){
                e.printStackTrace();
            }

            Kingdom.instance.kgetUserManager().removeUser(player);
            Kingdom.instance.kgetUserManager().addUser(player);

            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Cost.replace("%points%", String.valueOf(pointstoremove)));
        });
    }

    public static void AddPlayerPoints(Player player, int pointstoadd){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_targetdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata", player.getUniqueId() + ".yml"));
            final File targetdata = new File("plugins//Kingdom//playerdata//" + player.getUniqueId() + ".yml");
            int momentpoints = (int) cfg_targetdata.get("Points");
            cfg_targetdata.set("Points", momentpoints + pointstoadd);

            try {
                cfg_targetdata.save(targetdata);
            } catch (IOException e){
                e.printStackTrace();
            }

            Kingdom.instance.kgetUserManager().removeUser(player);
            Kingdom.instance.kgetUserManager().addUser(player);
        });
    }

    public static void SetPlayerRank (Player player, String rank){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final File playerfile = new File("plugins//Kingdom//playerdata//" + player.getUniqueId() + ".yml");
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final String kingdom = String.valueOf(cfg_playerdata.get("Kingdom".toLowerCase()));
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom + ".yml"));
            final String rankprefix = String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + rank + ".Prefix"));
            final String rankdisplay = String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + rank + ".Display"));
            final int rankweight = Kingdom.instance.kgetRanks().cfg_ranksfile.getInt("Ranks." + rank + ".Weight");
            final String kingdomtab = String.valueOf(cfg_kingdomdata.get("Tab"));
            final String ranksuffix = String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + Kingdom.instance.kGetConfig().Member_Rank + ".TabSuffix"));
            cfg_playerdata.set("Rank", rank);
            cfg_playerdata.set("Rank_Prefix", rankprefix);
            cfg_playerdata.set("Rank_Weight", rankweight);
            cfg_playerdata.set("Rank_Display", rankdisplay);
            try {
                cfg_playerdata.save(playerfile);
            } catch (IOException e){
                e.printStackTrace();
            }

            Kingdom.instance.kgetUserManager().removeUser(player);
            Kingdom.instance.kgetUserManager().addUser(player);
            NametagManager.getInstance().setNametag(player.getName(), (kingdomtab != null && !kingdomtab.equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', kingdomtab) : ""), (ranksuffix != null && !ranksuffix.equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', ranksuffix) : null));
        });
    }

    public static void SetPlayerKingdom (Player player, String kingdom){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final File playerfile = new File("plugins//Kingdom//playerdata//" + player.getUniqueId() + ".yml");
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms//", kingdom + ".yml"));
            final String kingdomprefix = String.valueOf(cfg_kingdomdata.get("Prefix"));
            final String kingdomname = String.valueOf(cfg_kingdomdata.get("Naam"));
            final String kingdomtab = String.valueOf(cfg_kingdomdata.get("Tab"));
            final String kingdomsoort = String.valueOf(cfg_kingdomdata.get("Soort"));
            final String kingdomdisplay = String.valueOf(cfg_kingdomdata.get("Display"));
            final String rankdisplay = String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + Kingdom.instance.kGetConfig().Member_Rank + ".Display"));
            final String rankprefix = String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + Kingdom.instance.kGetConfig().Member_Rank + ".Prefix"));
            final String rankweight = String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + Kingdom.instance.kGetConfig().Member_Rank + ".Weight"));
            final String ranksuffix = String.valueOf(Kingdom.instance.kgetRanks().cfg_ranksfile.get("Ranks." + Kingdom.instance.kGetConfig().Member_Rank + ".TabSuffix"));
            cfg_playerdata.set("Kingdom", kingdomname);
            cfg_playerdata.set("Kingdom_Prefix", kingdomprefix);
            cfg_playerdata.set("Kingdom_Display", kingdomdisplay);
            cfg_playerdata.set("Rank", Kingdom.instance.kGetConfig().Member_Rank);
            cfg_playerdata.set("Rank_Prefix", rankprefix);
            cfg_playerdata.set("Rank_Display", rankdisplay);
            cfg_playerdata.set("Rank_Weight", rankweight);
            cfg_playerdata.set("Tab", kingdomtab);
            cfg_playerdata.set("Soort", kingdomsoort);
            try {
                cfg_playerdata.save(playerfile);
            } catch (IOException e){
                e.printStackTrace();
            }
            Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom.toLowerCase());
            Kingdom.instance.kgetUserManager().removeUser(player);
            Kingdom.instance.kgetUserManager().addUser(player);
            NametagManager.getInstance().setNametag(player.getName(), (kingdomtab != null && !kingdomtab.equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', kingdomtab) : ""), (ranksuffix != null && !ranksuffix.equalsIgnoreCase("null") ? ChatColor.translateAlternateColorCodes('&', ranksuffix) : null));
            if (kingdoms.getSpawn() != null)
                player.teleport(kingdoms.getSpawn());
        });
    }

    public static void MakePlayerNO_KD (Player player){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final File playerfile = new File("plugins//Kingdom//playerdata//" + player.getUniqueId() + ".yml");
            final YamlConfiguration cfg_playerdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//playerdata//", player.getUniqueId() + ".yml"));
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
                cfg_playerdata.save(playerfile);
            } catch (IOException e){
                e.printStackTrace();
            }

            Kingdom.instance.kgetUserManager().removeUser(player);
            Kingdom.instance.kgetUserManager().addUser(player);
            NametagManager.getInstance().setNametag(player.getName(), ChatColor.translateAlternateColorCodes('&', Kingdom.instance.kGetConfig().NoKingdom_Tab), "");
        });
    }

    public static void AddAnAlly(String KingdomOfSender, String KingdomArgument, Player player){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            Kingdoms kingdom1 = Kingdom.instance.kgetKingdomManager().getKingdom(KingdomOfSender.toLowerCase());
            Kingdoms kingdom2 = Kingdom.instance.kgetKingdomManager().getKingdom(KingdomArgument.toLowerCase());

            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", KingdomOfSender.toLowerCase() + ".yml"));
            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + KingdomOfSender.toLowerCase() + ".yml");

            List<String> allies = new ArrayList<>();
            allies = kingdom1.getAllies();
            allies.add(KingdomArgument.toLowerCase());

            cfg_kingdomdata.set("Allies", allies);

            try {
                cfg_kingdomdata.save(kingdomfile);
            } catch (IOException e){
                e.printStackTrace();
            }

            for (Player players : Bukkit.getOnlinePlayers()){
                User u_players = Kingdom.instance.kgetUserManager().getUser(players);
                if (u_players.getKingdom().equalsIgnoreCase(KingdomOfSender)){
                    players.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Player_Casted_Ally
                            .replace("%player%", player.getName()).replace("%kingdom%", kingdom2.getOfficialName()));
                }
            }


                });
    }

    public static void RemoveAnAlly(String KingdomOfSender, String KingdomArgument, Player player){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            Kingdoms kingdom1 = Kingdom.instance.kgetKingdomManager().getKingdom(KingdomOfSender.toLowerCase());
            Kingdoms kingdom2 = Kingdom.instance.kgetKingdomManager().getKingdom(KingdomArgument.toLowerCase());

            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", KingdomOfSender.toLowerCase() + ".yml"));
            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + KingdomOfSender.toLowerCase() + ".yml");

            List<String> allies = new ArrayList<>();
            allies = kingdom1.getAllies();
            allies.remove(KingdomArgument.toLowerCase());

            cfg_kingdomdata.set("Allies", allies);

            try {
                cfg_kingdomdata.save(kingdomfile);
            } catch (IOException e){
                e.printStackTrace();
            }

            for (Player players : Bukkit.getOnlinePlayers()){
                User u_players = Kingdom.instance.kgetUserManager().getUser(players);
                if (u_players.getKingdom().equalsIgnoreCase(KingdomOfSender)){
                    players.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Succesfull_Neutral_All
                            .replace("%player%", player.getName()).replace("%kingdom%", kingdom2.getOfficialName()));
                }
            }


        });
    }

    public static void SendJoinMessage(Player player){
        for(String messaga : Kingdom.instance.kGetConfig().JoinMessage){
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', messaga)));
        }
    }

    public static String getPlayerREGION(Player player){
        return PlaceholderAPI.setPlaceholders(player, "%worldguard_region_name_capitalized%");
    }

    public static String getRegion(Location location) {
        RegionContainer container = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet regions = query.getApplicableRegions(BukkitAdapter.adapt(location));
        return regions.getRegions().stream().findAny().map(ProtectedRegion::getId).orElse(null);
    }

    public static ApplicableRegionSet getRegions(Location location) {
        RegionContainer container = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet regions = query.getApplicableRegions(BukkitAdapter.adapt(location));
        return regions;
    }

}
