package nl.snowpix.kingdom.system;

import nl.snowpix.kingdom.Kingdom;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Ranks {

    //MAKING A RANKS.YML FILE.

    public ArrayList<String> ALL_RANKS = new ArrayList();

    public YamlConfiguration cfg_ranksfile = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//ranks", "ranks.yml"));
    public File ranksfile = new File("plugins//Kingdom//ranks//ranks.yml");

    public void CheckRanks(){
        ALL_RANKS.clear();
        if (!ranksfile.exists()){
            ArrayList<String> KoningPerms = new ArrayList();
            ArrayList<String> HertogPerms = new ArrayList();
            ArrayList<String> BurgerPerms = new ArrayList();
            KoningPerms.add("kingdom.kick");
            KoningPerms.add("kingdom.setrank");
            KoningPerms.add("kingdom.invite");
            KoningPerms.add("kingdom.ally");
            KoningPerms.add("kingdom.neutral");
            KoningPerms.add("kingdom.setspawn");
            KoningPerms.add("kingdom.join");
            KoningPerms.add("kingdom.spawn");
            HertogPerms.add("kingdom.kick");
            HertogPerms.add("kingdom.invite");
            HertogPerms.add("kingdom.join");
            HertogPerms.add("kingdom.spawn");
            BurgerPerms.add("kingdom.join");
            BurgerPerms.add("kingdom.spawn");
            cfg_ranksfile.set("Ranks.Koning.Prefix", "&7[&6Koning&7]");
            cfg_ranksfile.set("Ranks.Koning.Display", "&6Koning");
            cfg_ranksfile.set("Ranks.Koning.TabSuffix", "&7[&6K&7]");
            cfg_ranksfile.set("Ranks.Koning.Weight", 3);
            cfg_ranksfile.set("Ranks.Koning.Permissions", KoningPerms);
            cfg_ranksfile.set("Ranks.Hertog.Prefix", "&7[&2Hertog&7]");
            cfg_ranksfile.set("Ranks.Hertog.Display", "&2Hertog");
            cfg_ranksfile.set("Ranks.Hertog.Weight", 2);
            cfg_ranksfile.set("Ranks.Hertog.Permissions", HertogPerms);
            cfg_ranksfile.set("Ranks.Burger.Prefix", "&7[&8Burger&7]");
            cfg_ranksfile.set("Ranks.Burger.Display", "&8Burger");
            cfg_ranksfile.set("Ranks.Burger.Weight", 1);
            cfg_ranksfile.set("Ranks.Burger.Permissions", BurgerPerms);
            ALL_RANKS.add("Hertog");
            ALL_RANKS.add("Koning");
            ALL_RANKS.add("Burger");
            try {
                cfg_ranksfile.save(ranksfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Set<String> ranks = cfg_ranksfile.getConfigurationSection("Ranks").getKeys(false);
            if (!ranks.contains(Kingdom.instance.kGetConfig().Member_Rank)){
                System.out.println(SystemColors.TEXT_YELLOW + "[KINGDOM] " + SystemColors.TEXT_RED + "THE MEMBER RANK OF THE CONFIG CAN NOT BE FOUND! THIS CAN BE CASE SENSITIVE!");
                System.out.println(SystemColors.TEXT_YELLOW + "[KINGDOM] " + SystemColors.TEXT_RED + "The Rank that needs to be in the ranks.yml: '" + Kingdom.instance.kGetConfig().Member_Rank+ "'");
            }
            for (String test : ranks){
                ALL_RANKS.add(test);
            }
        }
    }




}
