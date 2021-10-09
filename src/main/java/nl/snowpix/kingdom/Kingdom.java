package nl.snowpix.kingdom;

import nl.snowpix.kingdom.data.KingdomsManager;
import nl.snowpix.kingdom.data.UserManager;
import nl.snowpix.kingdom.events.BlockBreakMine;
import nl.snowpix.kingdom.system.KingdomMethods;
import nl.snowpix.kingdom.api.PlaceholderHook;
import nl.snowpix.kingdom.system.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public final class Kingdom extends JavaPlugin {

    private Config config;
    private PointsConfig pointsConfig;
    private Messages messages;
    private Ranks ranks;
    private KingdomMethods kingdomMethods;
    private KingdomsManager kingdomsManager;
    private UserManager userManager;
    public static Kingdom instance;
    public FileConfiguration c;

    @Override
    public void onEnable() {

        instance = this;
        c = getConfig();

        config = new Config();
        pointsConfig = new PointsConfig();
        ranks = new Ranks();
        messages = new Messages();
        kingdomMethods = new KingdomMethods();
        userManager = new UserManager();
        kingdomsManager = new KingdomsManager();

        config.ReloadConfig();

        Register.RegisterEvents();

        messages.checkMessages();
        messages.reloadMessages();
        ranks.CheckRanks();
        kingdomMethods.LoadAllKingdoms();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI")!=null){
            new PlaceholderHook().register();
        }else{
            System.out.println(SystemColors.TEXT_YELLOW + "[KINGDOM] " + SystemColors.TEXT_RED + "YOU NEED TO INSTALL THE PLUGIN: PlaceholderAPI, OR THE PLUGIN WILL NOT WORK!" + SystemColors.TEXT_RESET);
        }

        config.CheckPlugin("WorldGuard");
        config.CheckPlugin("WorldGuardEvents");
        config.CheckPlugin("WorldEdit");

        config.CheckPlayers();

        for (final Team t : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
            t.unregister();
        }


        System.out.println(SystemColors.TEXT_YELLOW + "[KINGDOM] " + SystemColors.TEXT_GREEN + "The plugin is succesfully enabled." + SystemColors.TEXT_RESET);

        kingdomMethods.CheckKingdomsOnLoad();

    }

    @Override
    public void onDisable() {

        for (Location locs : BlockBreakMine.blocksMap.keySet()){
            locs.getBlock().setType(BlockBreakMine.blocksMap.get(locs));
        }

        System.out.println(SystemColors.TEXT_BLUE + "[Kingdom] " + SystemColors.TEXT_GREEN + "De plugin staat succesvol uit!" + SystemColors.TEXT_RESET);
    }

    //CONSTRUCTORS

    public Messages kgetMessages(){
        return messages;
    }

    public Config kGetConfig(){
        return config;
    }

    public PointsConfig kgetPointsConfig(){
        return pointsConfig;
    }

    public Ranks kgetRanks(){
        return ranks;
    }

    public KingdomMethods kgetKingdomsMethods(){
        return kingdomMethods;
    }

    public KingdomsManager kgetKingdomManager(){
        return kingdomsManager;
    }

    public UserManager kgetUserManager(){
        return userManager;
    }


}
