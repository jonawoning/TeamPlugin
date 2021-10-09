package nl.snowpix.kingdom.methods;

import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EditListener {

    public static void EditPrefix(String kingdom, String prefix){
        String tprefix = ChatColor.translateAlternateColorCodes('&', prefix);
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {

            final YamlConfiguration cfg_kingdomfile = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", kingdom.toLowerCase() + ".yml"));
            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kingdom.toLowerCase() + ".yml");

            if (kingdomfile.exists()){

                cfg_kingdomfile.set("Prefix", tprefix);

                try {
                    cfg_kingdomfile.save(kingdomfile);
                } catch (IOException e){
                    e.printStackTrace();
                }


                Kingdom.instance.kgetKingdomsMethods().LoadAllKingdoms();

                Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom.toLowerCase());
                kingdoms.setKingdomPrefix(tprefix);
            }

        });
    }

    public static void EditDisplay(String kingdom, String display){
        String tdisplay = ChatColor.translateAlternateColorCodes('&', display);
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {

            final YamlConfiguration cfg_kingdomfile = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", kingdom.toLowerCase() + ".yml"));
            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kingdom.toLowerCase() + ".yml");

            if (kingdomfile.exists()){

                cfg_kingdomfile.set("Display", tdisplay);

                try {
                    cfg_kingdomfile.save(kingdomfile);
                } catch (IOException e){
                    e.printStackTrace();
                }


                Kingdom.instance.kgetKingdomsMethods().LoadAllKingdoms();
                Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom.toLowerCase());
                kingdoms.setKingdomDisplay(tdisplay);
            }

        });
    }

    public static void EditTab(String kingdom, String tab){
        String tTab = ChatColor.translateAlternateColorCodes('&', tab);
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {

            final YamlConfiguration cfg_kingdomfile = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", kingdom.toLowerCase() + ".yml"));
            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kingdom.toLowerCase() + ".yml");

            if (kingdomfile.exists()){

                cfg_kingdomfile.set("Tab", tTab);

                try {
                    cfg_kingdomfile.save(kingdomfile);
                } catch (IOException e){
                    e.printStackTrace();
                }


                Kingdom.instance.kgetKingdomsMethods().LoadAllKingdoms();

                Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom.toLowerCase());
                kingdoms.setKingdomTab(tTab);
            }

        });
    }

    public static void EditSoort(String kingdom, String soort){
        String tSoort = ChatColor.translateAlternateColorCodes('&', soort);
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {

            final YamlConfiguration cfg_kingdomfile = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", kingdom.toLowerCase() + ".yml"));
            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kingdom.toLowerCase() + ".yml");

            if (kingdomfile.exists()){

                cfg_kingdomfile.set("Soort", tSoort);

                try {
                    cfg_kingdomfile.save(kingdomfile);
                } catch (IOException e){
                    e.printStackTrace();
                }


                Kingdom.instance.kgetKingdomsMethods().LoadAllKingdoms();

                Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom.toLowerCase());
                kingdoms.setSoort(tSoort);
            }

        });
    }
}
