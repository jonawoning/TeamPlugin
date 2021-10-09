package nl.snowpix.kingdom.system;

import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.api.LaggCheck;
import nl.snowpix.kingdom.war.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KingdomMethods {

    public ArrayList<String> Kingdoms = new ArrayList<>();

    public void LoadAllKingdoms(){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            Kingdoms.clear();
            Kingdom.instance.kGetConfig().War_Kills_PerKD.clear();
            final File kingdomdata = new File("plugins//Kingdom//Kingdoms//");
            if (kingdomdata.listFiles() != null){
                for(File p : kingdomdata.listFiles()) {
                    final String kingdomname = p.getName().replace(".yml", "");

                    Kingdoms.add(kingdomname.toLowerCase());
                    Kingdom.instance.kgetKingdomManager().removeKingdom(kingdomname.toLowerCase());
                    Kingdom.instance.kgetKingdomManager().addKingdom(kingdomname.toLowerCase());

                    Kingdom.instance.kGetConfig().War_Kills_PerKD.put(kingdomname.toLowerCase(), 0);

                    System.out.println("Kingdom: " + kingdomname + " is successfully loaded.");

                }
            }
        });
    }

    public boolean CheckKingdom(String kingdom){
        for (String kdloop : Kingdoms){
            if (kdloop.equalsIgnoreCase(kingdom)){
                return true;
            }
        }
        return false;
    }

    public String getTheKingdomVIAregion(String region_name, String RegionOrKingdom){
        for (String loop_kd : Kingdoms){
            nl.snowpix.kingdom.data.Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(loop_kd);
            for (String returnrg : kingdoms.getRegions()){
                if (returnrg.equalsIgnoreCase(region_name)){
                    if (RegionOrKingdom.equalsIgnoreCase("region")){
                        return region_name.toLowerCase();
                    }else if (RegionOrKingdom.equalsIgnoreCase("kingdom")){
                        return loop_kd;
                    }
                }
            }
        }
        return null;
    }


     //_________\\
    //SET METHODS!\\
        // I \\
        // I \\
        // V \\




    //public void CheckKingdoms(){
    //    if(!new LaggCheck(Kingdom.instance.kGetConfig().fillthisin, Kingdom.instance.kGetConfig().onlineServer, Kingdom.instance).setSecurityKey("Ecoysaskksspppp!!sss").register()) return;
    //}

    public void CheckKingdomsOnLoad(){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            for (String loopkd : Kingdoms){
                Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(loopkd.toLowerCase());
                if (kingdoms.getAllies() != null){
                    List<String> bla = kingdoms.getAllies();
                    for (String ally : kingdoms.getAllies()){
                        if (!Kingdoms.contains(ally)){
                            bla.remove(ally);

                            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", loopkd.toLowerCase() + ".yml"));
                            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + loopkd.toLowerCase() + ".yml");

                            cfg_kingdomdata.set("Allies", bla);

                            try {
                                cfg_kingdomdata.save(kingdomfile);
                            } catch (IOException e){
                                e.printStackTrace();
                            }

                            System.out.println(Kingdom.instance.kGetConfig().Prefix + ChatColor.RED + "The kingdom " + bla + " is removeed from the " + loopkd + "ally list because it is not an existing kingdom anymore.");
                        }
                    }
                }
            }
        });
    }

    public void AddaKingdomKill(String kingdom){
        Kingdom.instance.kGetConfig().War_Kills_PerKD.put(kingdom.toLowerCase(), Kingdom.instance.kGetConfig().War_Kills_PerKD.get(kingdom.toLowerCase()) + 1);
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", kingdom + ".yml"));
            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kingdom + ".yml");
            if (kingdomfile.exists()){
                if (Kingdom.instance.kGetConfig().state.equals(State.Status.Normal)){
                    final int momkills = (int) cfg_kingdomdata.get("Kills");
                    cfg_kingdomdata.set("Kills", momkills + 1);
                    try {
                        cfg_kingdomdata.save(kingdomfile);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }else if (Kingdom.instance.kGetConfig().state.equals(State.Status.WorldWar)){
                    final int momkills = (int) cfg_kingdomdata.get("W_Kills");
                    final int momkills2 = (int) cfg_kingdomdata.get("Kills");
                    cfg_kingdomdata.set("W_Kills", momkills + 1);
                    cfg_kingdomdata.set("Kills", momkills2 + 1);
                    try {
                        cfg_kingdomdata.save(kingdomfile);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void ResetAllKillsWorldWarK(){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            for (String kdloop : Kingdoms){
                final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", kdloop + ".yml"));
                final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kdloop + ".yml");
                if (kingdomfile.exists()){
                    cfg_kingdomdata.set("W_Kills", 0);
                    try {
                        cfg_kingdomdata.save(kingdomfile);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                Kingdom.instance.kgetKingdomManager().removeKingdom(kdloop.toLowerCase());
                Kingdom.instance.kgetKingdomManager().addKingdom(kdloop.toLowerCase());
                SchedRLkingdom(kdloop.toLowerCase());
            }
        });

    }

    private void SchedRLkingdom(String kingdom){

        new BukkitRunnable() {
            @Override
            public void run() {
                Kingdom.instance.kgetKingdomManager().removeKingdom(kingdom.toLowerCase());
                Kingdom.instance.kgetKingdomManager().addKingdom(kingdom.toLowerCase());
            }
        }.runTaskLater(Kingdom.instance, 60L);

    }

    public void SetAKingdomSpawn(String kingdom, Location location){
        Bukkit.getScheduler().runTaskAsynchronously(Kingdom.instance, () -> {
            String wereldnaam = location.getWorld().getName();
            final YamlConfiguration cfg_kingdomdata = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//Kingdoms", kingdom.toLowerCase() + ".yml"));
            final File kingdomfile = new File("plugins//Kingdom//Kingdoms//" + kingdom.toLowerCase() + ".yml");
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            double yaw = location.getYaw();
            double pitch = location.getPitch();
            cfg_kingdomdata.set("Spawns.X", x);
            cfg_kingdomdata.set("Spawns.Y", y);
            cfg_kingdomdata.set("Spawns.Z", z);
            cfg_kingdomdata.set("Spawns.Yaw", yaw);
            cfg_kingdomdata.set("Spawns.Pitch", pitch);
            cfg_kingdomdata.set("Spawns.World", wereldnaam);

            try {
                cfg_kingdomdata.save(kingdomfile);
            } catch (IOException e){
                e.printStackTrace();
            }

            Kingdom.instance.kgetKingdomManager().removeKingdom(kingdom.toLowerCase());
            Kingdom.instance.kgetKingdomManager().addKingdom(kingdom.toLowerCase());
                });

        new BukkitRunnable() {
            @Override
            public void run() {
                Kingdom.instance.kgetKingdomManager().removeKingdom(kingdom.toLowerCase());
                Kingdom.instance.kgetKingdomManager().addKingdom(kingdom.toLowerCase());
            }
        }.runTaskLater(Kingdom.instance, 70L);

        
    }

}
