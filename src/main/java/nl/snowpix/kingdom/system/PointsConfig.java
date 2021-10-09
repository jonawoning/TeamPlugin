package nl.snowpix.kingdom.system;

import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.methods.ExtraMethods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PointsConfig {

    public int Breaks_Chest;
    public int Breaks_TrappedChest;
    public int Breaks_Beacon;
    public int Breaks_DiamondBlock;
    public int Breaks_Other;

    public int Place_Bucket;
    public int Place_TNT;
    public int Place_Obsidian;
    public int Place_Enderchest;
    public int Place_Other;

    public int Use_Chest;
    public int Use_TrappedChest;
    public int Use_Enderchest;
    public int Use_Door;
    public int Use_Fence_Gate;
    public int Use_Trapdoor;
    public int Use_Other;

    public boolean Use_Points_System;
    public boolean Allies_Bypass;
    public int Bounty_Seconds;
    public boolean Use_Bounty;
    public int Max_Points;

    public void LoadAllVariables(){
        Breaks_Chest = Kingdom.instance.c.getInt("Points_Cost.Breaks.Chest");
        Breaks_TrappedChest = Kingdom.instance.c.getInt("Points_Cost.Breaks.TrappedChest");
        Breaks_Beacon = Kingdom.instance.c.getInt("Points_Cost.Breaks.Beacon");
        Breaks_DiamondBlock = Kingdom.instance.c.getInt("Points_Cost.Breaks.DiamondBlock");
        Breaks_Other = Kingdom.instance.c.getInt("Points_Cost.Breaks.Other");

        Place_Bucket = Kingdom.instance.c.getInt("Points_Cost.Place.Bucket");
        Place_TNT = Kingdom.instance.c.getInt("Points_Cost.Place.TNT");
        Place_Obsidian = Kingdom.instance.c.getInt("Points_Cost.Place.Obsidian");
        Place_Enderchest = Kingdom.instance.c.getInt("Points_Cost.Place.Enderchest");
        Place_Other = Kingdom.instance.c.getInt("Points_Cost.Place.Other");

        Use_Chest = Kingdom.instance.c.getInt("Points_Cost.Use.Chest");
        Use_TrappedChest = Kingdom.instance.c.getInt("Points_Cost.Use.TappedChest");
        Use_Enderchest = Kingdom.instance.c.getInt("Points_Cost.Use.Enderchest");
        Use_Door = Kingdom.instance.c.getInt("Points_Cost.Use.Door");
        Use_Fence_Gate = Kingdom.instance.c.getInt("Points_Cost.Use.Fence_Gate");
        Use_Trapdoor = Kingdom.instance.c.getInt("Points_Cost.Use.Trapdoor");
        Use_Other = Kingdom.instance.c.getInt("Points_Cost.Use.Other");

        Use_Points_System = Kingdom.instance.c.getBoolean("Use_Points_System");
        Allies_Bypass = Kingdom.instance.c.getBoolean("Allies_Bypass");
        Bounty_Seconds = Kingdom.instance.c.getInt("Bounty_Seconds");
        Use_Bounty = Kingdom.instance.c.getBoolean("Use_Bounty");
        Max_Points = Kingdom.instance.c.getInt("Max_Points");
    }

    public void LoopPoints(){
        if (Use_Bounty){
            new BukkitRunnable() {

                @Override
                public void run() {

                    for (Player player : Bukkit.getOnlinePlayers()){
                        User user = Kingdom.instance.kgetUserManager().getUser(player);
                        if (!(user.getPoints() >= Kingdom.instance.kgetPointsConfig().Max_Points)){
                            ExtraMethods.AddPlayerPoints(player, Kingdom.instance.kgetPointsConfig().Bounty_Seconds);
                        }
                    }
                    Bukkit.broadcastMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Bounty_Message_ALL.replace("%points%", String.valueOf(Kingdom.instance.kgetPointsConfig().Bounty_Seconds)));
                }
            }.runTaskTimerAsynchronously(Kingdom.instance, 0L, 6000L);
        }
    }



}
