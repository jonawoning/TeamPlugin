package nl.snowpix.teamplugin.system;

import nl.snowpix.teamplugin.TeamPlugin;
import nl.snowpix.teamplugin.commands.TeamCommand;
import nl.snowpix.teamplugin.events.onChat;
import nl.snowpix.teamplugin.events.onJoin;
import nl.snowpix.teamplugin.events.onLeave;

public class Registerer {

    public static void LoadEvents(){
        TeamPlugin.instance.getServer().getPluginManager().registerEvents(new onJoin(), TeamPlugin.instance);
        TeamPlugin.instance.getServer().getPluginManager().registerEvents(new onLeave(), TeamPlugin.instance);
        TeamPlugin.instance.getServer().getPluginManager().registerEvents(new onChat(), TeamPlugin.instance);
        TeamPlugin.instance.getCommand("teams").setExecutor(new TeamCommand());
    }

}
