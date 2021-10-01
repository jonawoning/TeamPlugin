package nl.snowpix.teamplugin;

import nl.snowpix.teamplugin.data.TeamP.TeamManager;
import nl.snowpix.teamplugin.data.UserP.UserManager;
import nl.snowpix.teamplugin.methods.SystemMethods;
import nl.snowpix.teamplugin.placeholders.PlaceholderHook;
import nl.snowpix.teamplugin.system.Config;
import nl.snowpix.teamplugin.system.Messages;
import nl.snowpix.teamplugin.system.SystemColors;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeamPlugin extends JavaPlugin {


    private Config config;
    private Messages messages;
    private TeamManager teamManager;
    private UserManager userManager;
    private SystemMethods systemMethods;

    public static TeamPlugin instance;
    public FileConfiguration c;

    @Override
    public void onEnable() {

        c = getConfig();
        instance = this;

        config = new Config();
        teamManager = new TeamManager();
        userManager = new UserManager();
        systemMethods = new SystemMethods();
        messages = new Messages();

        config.ReloadConfig();

        messages.checkMessages();
        messages.reloadMessages();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI")!=null){
            new PlaceholderHook().register();
        }else{
            System.out.println(SystemColors.TEXT_YELLOW + "[TeamPlugin] " + SystemColors.TEXT_RED + "YOU NEED TO INSTALL THE PLUGIN: PlaceholderAPI, OR THE PLUGIN WILL NOT WORK!" + SystemColors.TEXT_RESET);
        }

        System.out.println(SystemColors.TEXT_GREEN + "[TeamPlugin] The plugin is succesfully enabled." + SystemColors.TEXT_RESET);

    }

    @Override
    public void onDisable() {
        System.out.println(SystemColors.TEXT_GREEN + "[TeamPlugin] The plugin is succesfully disabled." + SystemColors.TEXT_RESET);
    }

    public Config kgetConfig(){
        return config;
    }

    public TeamManager kgetTeamManager(){
        return teamManager;
    }

    public UserManager kgetUserManager(){
        return userManager;
    }

    public SystemMethods kgetSystemMethods(){
        return systemMethods;
    }

    public Messages kgetMessages(){
        return messages;
    }

}
