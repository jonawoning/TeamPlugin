package nl.snowpix.kingdom.system;

import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.commands.*;
import nl.snowpix.kingdom.events.*;

public class Register {

    public static void RegisterEvents(){
        Kingdom.instance.getServer().getPluginManager().registerEvents(new onJoin(), Kingdom.instance);
        Kingdom.instance.getServer().getPluginManager().registerEvents(new onChat(), Kingdom.instance);
        Kingdom.instance.getServer().getPluginManager().registerEvents(new onDamage(), Kingdom.instance);
        Kingdom.instance.getServer().getPluginManager().registerEvents(new onLeave(), Kingdom.instance);
        Kingdom.instance.getServer().getPluginManager().registerEvents(new WGEvents(), Kingdom.instance);
        Kingdom.instance.getServer().getPluginManager().registerEvents(new onMove(), Kingdom.instance);
        Kingdom.instance.getServer().getPluginManager().registerEvents(new onRespawn(), Kingdom.instance);
        Kingdom.instance.getServer().getPluginManager().registerEvents(new BlockBreakMine(), Kingdom.instance);
        Kingdom.instance.getCommand("staff").setExecutor(new Staff());
        Kingdom.instance.getCommand("kingdom").setExecutor(new KingdomCommand());
        Kingdom.instance.getCommand("edit").setExecutor(new EditCommand());
        Kingdom.instance.getCommand("tl").setExecutor(new TLCommand());
        Kingdom.instance.getCommand("points").setExecutor(new Points());
        Kingdom.instance.getCommand("mineisland").setExecutor(new MineIsland());
        Kingdom.instance.getServer().getMessenger().registerOutgoingPluginChannel(Kingdom.instance, "BungeeCord");
    }

}
