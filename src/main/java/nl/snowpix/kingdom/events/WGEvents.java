package nl.snowpix.kingdom.events;

import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;
import nl.snowpix.kingdom.data.Getters;
import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import nl.snowpix.kingdom.commands.Points;
import nl.snowpix.kingdom.methods.ExtraMethods;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WGEvents implements Listener {

    @EventHandler
    private void onRegionEnter(RegionEnteredEvent e){
        final Player player = e.getPlayer();
        User user = Kingdom.instance.kgetUserManager().getUser(player);
        if (Kingdom.instance.kGetConfig().UseRegionEvents){
            final String regionname = e.getRegion().getId();
            Getters.getPlayerKD(player).whenComplete((result, throwable) -> {
                if (result != null) {
                    final String kingdomDisplayPlayer = user.getKingdom_Display();
                    if (regionname.equalsIgnoreCase(Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(regionname, "region"))){

                        final String kingdomofregion = Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(regionname, "kingdom");
                        Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomofregion);


                        if (kingdomDisplayPlayer.equalsIgnoreCase(kingdoms.getKingdomDisplay())){
                            player.sendMessage(Kingdom.instance.kGetConfig().OwnKingdomRegionEnter.replace("%region%", kingdoms.getKingdomDisplay()));
                            player.sendTitle(Kingdom.instance.kGetConfig().OwnKD_Region_EN_TITLE.replace("%region%", kingdoms.getKingdomDisplay()), Kingdom.instance.kGetConfig().OwnKD_Region_EN_SUBTITLE.replace("%region%", kingdoms.getKingdomDisplay()), 10, 30, 20);
                        }else{
                            player.sendMessage(Kingdom.instance.kGetConfig().RegionEnter.replace("%region%", kingdoms.getKingdomDisplay()));
                            player.sendTitle(Kingdom.instance.kGetConfig().RegionEnter_Title.replace("%region%", kingdoms.getKingdomDisplay()), Kingdom.instance.kGetConfig().RegionEnter_SubTitle.replace("%region%", kingdoms.getKingdomDisplay()), 10, 30, 20);
                        }
                    }else{
                        if (Kingdom.instance.kGetConfig().MessageOnEveryRegion){
                            player.sendMessage(Kingdom.instance.kGetConfig().RegionEnter.replace("%region%", StringUtils.capitalize(regionname)));
                            player.sendTitle(Kingdom.instance.kGetConfig().RegionEnter_Title.replace("%region%", StringUtils.capitalize(regionname)), Kingdom.instance.kGetConfig().RegionEnter_SubTitle.replace("%region%", StringUtils.capitalize(regionname)), 10, 30, 20);
                        }
                    }
                }
            });


        }

    }

    @EventHandler
    private void onRegionLeave(RegionLeftEvent e){
        final Player player = e.getPlayer();
        User user = Kingdom.instance.kgetUserManager().getUser(player);
        if (Kingdom.instance.kGetConfig().UseRegionEvents){
            final String regionname = e.getRegion().getId();
            if (user.getKingdom_Display() != null){
                final String kingdomDisplayPlayer = user.getKingdom_Display();
                if (regionname.equalsIgnoreCase(Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(regionname, "region"))){

                    final String kingdomofregion = Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(regionname, "kingdom");
                    Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomofregion);

                    if (kingdomDisplayPlayer.equalsIgnoreCase(kingdoms.getKingdomDisplay())){
                        player.sendMessage(Kingdom.instance.kGetConfig().OwnKingdomRegionLeave.replace("%region%", kingdoms.getKingdomDisplay()));
                        player.sendTitle(Kingdom.instance.kGetConfig().OwnKD_Region_LE_TITLE.replace("%region%", kingdoms.getKingdomDisplay()), Kingdom.instance.kGetConfig().OwnKD_Region_LE_SUBTITLE.replace("%region%", kingdoms.getKingdomDisplay()), 10, 30, 20);
                    }else{
                        player.sendMessage(Kingdom.instance.kGetConfig().RegionLeave.replace("%region%", kingdoms.getKingdomDisplay()));
                        player.sendTitle(Kingdom.instance.kGetConfig().RegionLeave_Title.replace("%region%", kingdoms.getKingdomDisplay()), Kingdom.instance.kGetConfig().RegionLeave_SubTitle.replace("%region%", kingdoms.getKingdomDisplay()), 10, 30, 20);
                    }
                }else{
                    if (Kingdom.instance.kGetConfig().MessageOnEveryRegion){
                        player.sendMessage(Kingdom.instance.kGetConfig().RegionLeave.replace("%region%", StringUtils.capitalize(regionname)));
                        player.sendTitle(Kingdom.instance.kGetConfig().RegionLeave_Title.replace("%region%", StringUtils.capitalize(regionname)), Kingdom.instance.kGetConfig().RegionLeave_SubTitle.replace("%region%", StringUtils.capitalize(regionname)), 10, 30, 20);
                    }
                }
            }
        }


    }

    public boolean allyCheck(String kingdom1, String kingdom2, Location location){
        Kingdoms kingdoms1 = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom1.toLowerCase());
        Kingdoms kingdoms2 = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom2.toLowerCase());
        if (Kingdom.instance.kgetPointsConfig().Allies_Bypass){
            if (kingdoms1.getRegions().contains(ExtraMethods.getRegion(location))){
                if (kingdoms1.getAllies().contains(kingdoms2.getKingdomName())){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        if (Kingdom.instance.kgetPointsConfig().Use_Points_System){
            final Player player = e.getPlayer();
            User user = Kingdom.instance.kgetUserManager().getUser(player);

            if (ExtraMethods.getRegion(e.getBlock().getLocation()) != null) {

                if (!Points.pointsbypass.contains(player)) {

                    final Material block = e.getBlock().getType();
                    final String kingdomDisplayPlayer = user.getKingdom_Display();
                    final int points_player = user.getPoints();
                    final String region_block = String.valueOf(ExtraMethods.getRegion(e.getBlock().getLocation()));

                    if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)) {

                        if (region_block.equalsIgnoreCase(Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "region"))) {

                            final String kingdomofregion = Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "kingdom");
                            Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomofregion.toLowerCase());

                            //CHECKS IF THE REGION EQUALS THE KINGDOM OF THE PLAYER.
                            if (!kingdomDisplayPlayer.equalsIgnoreCase(kingdoms.getKingdomDisplay())) {

                                //CHECKS FOR ALLY BYPASS.
                                if (!allyCheck(kingdomofregion, user.getKingdom().toLowerCase(), e.getBlock().getLocation())) {

                                    //CHECKING BLOCKS

                                    if (block.equals(Material.CHEST)) {
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Breaks_Chest)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Breaks_Chest);
                                        }
                                    } else if (block.equals(Material.TRAPPED_CHEST)) {
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Breaks_TrappedChest)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Breaks_TrappedChest);
                                        }
                                    } else if (block.equals(Material.BEACON)) {
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Breaks_Beacon)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Breaks_Beacon);
                                        }
                                    } else if (block.equals(Material.DIAMOND_BLOCK)) {
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Breaks_DiamondBlock)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Breaks_DiamondBlock);
                                        }
                                    } else if (block.equals(Material.WATER)) {
                                        if (player.getInventory().getItemInMainHand().equals(Material.WATER_BUCKET) && (Kingdom.instance.kgetPointsConfig().Place_Bucket != 0)){
                                            if (!(points_player >= Kingdom.instance.kgetPointsConfig().Place_Bucket)) {
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                                e.setCancelled(true);
                                            } else {
                                                ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Place_Bucket);
                                            }
                                        }
                                    } else {
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Breaks_Other)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Breaks_Other);
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        if (!player.hasPermission("kingdom.pointsbypass")) {
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e){
        if (Kingdom.instance.kgetPointsConfig().Use_Points_System){
            final Player player = e.getPlayer();
            User user = Kingdom.instance.kgetUserManager().getUser(player);

            if (ExtraMethods.getRegion(e.getBlock().getLocation()) != null) {

                if (!Points.pointsbypass.contains(player)) {

                    final Material block = e.getBlock().getType();
                    final String kingdomDisplayPlayer = user.getKingdom_Display();
                    final int points_player = user.getPoints();
                    final String region_block = String.valueOf(ExtraMethods.getRegion(e.getBlock().getLocation()));

                    if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)) {

                        if (region_block.equalsIgnoreCase(Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "region"))) {

                            final String kingdomofregion = Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "kingdom");
                            Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomofregion.toLowerCase());

                            //CHECKS IF THE REGION EQUALS THE KINGDOM OF THE PLAYER.
                            if (!kingdomDisplayPlayer.equalsIgnoreCase(kingdoms.getKingdomDisplay())) {

                                //CHECKS FOR ALLY BYPASS.
                                if (!allyCheck(kingdomofregion, user.getKingdom().toLowerCase(), e.getBlock().getLocation())) {

                                    //CHECKING BLOCKS

                                    if (block.equals(Material.TNT)) {
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Place_TNT)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Place_TNT);
                                        }
                                    } else if (block.equals(Material.OBSIDIAN)) {
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Place_Obsidian)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Place_Obsidian);
                                        }
                                    }else if (block.equals(Material.ENDER_CHEST)) {
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Place_Enderchest)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Place_Enderchest);
                                        }
                                    }else{
                                        if (!(points_player >= Kingdom.instance.kgetPointsConfig().Place_Other)) {
                                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                            e.setCancelled(true);
                                        } else {
                                            ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Place_Other);
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        if (!player.hasPermission("kingdom.pointsbypass")) {
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void OnInteract(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (Kingdom.instance.kgetPointsConfig().Use_Points_System){
                final Player player = e.getPlayer();
                User user = Kingdom.instance.kgetUserManager().getUser(player);

                if (ExtraMethods.getRegion(e.getClickedBlock().getLocation()) != null) {

                    if (!Points.pointsbypass.contains(player)) {

                        final Material block = e.getClickedBlock().getType();
                        final String kingdomDisplayPlayer = user.getKingdom_Display();
                        final int points_player = user.getPoints();
                        final String region_block = String.valueOf(ExtraMethods.getRegion(e.getClickedBlock().getLocation()));

                        if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)) {

                            if (region_block.equalsIgnoreCase(Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "region"))) {

                                final String kingdomofregion = Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "kingdom");
                                Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomofregion.toLowerCase());

                                //CHECKS IF THE REGION EQUALS THE KINGDOM OF THE PLAYER.
                                if (!kingdomDisplayPlayer.equalsIgnoreCase(kingdoms.getKingdomDisplay())) {

                                    //CHECKS FOR ALLY BYPASS.
                                    if (!allyCheck(kingdomofregion, user.getKingdom().toLowerCase(), e.getClickedBlock().getLocation())) {

                                        //CHECKING BLOCKS

                                        if (block.equals(Material.CHEST)) {
                                            if (!(points_player >= Kingdom.instance.kgetPointsConfig().Use_Chest)) {
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                                e.setCancelled(true);
                                            } else {
                                                ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Use_Chest);
                                            }
                                        } else if (block.equals(Material.TRAPPED_CHEST)) {
                                            if (!(points_player >= Kingdom.instance.kgetPointsConfig().Use_TrappedChest)) {
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                                e.setCancelled(true);
                                            } else {
                                                ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Use_TrappedChest);
                                            }
                                        }else if (block.equals(Material.ENDER_CHEST)) {
                                            if (!(points_player >= Kingdom.instance.kgetPointsConfig().Use_Enderchest)) {
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                                e.setCancelled(true);
                                            } else {
                                                ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Use_Enderchest);
                                            }
                                        }else if (block.equals(Material.OAK_DOOR) || (block.equals(Material.ACACIA_DOOR) || (block.equals(Material.BIRCH_DOOR) || (block.equals(Material.DARK_OAK_DOOR) || (block.equals(Material.CRIMSON_DOOR) || block.equals(Material.JUNGLE_DOOR) || (block.equals(Material.SPRUCE_DOOR) || (block.equals(Material.WARPED_DOOR)))))))){
                                            if (!(points_player >= Kingdom.instance.kgetPointsConfig().Use_Enderchest)) {
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                                e.setCancelled(true);
                                            } else {
                                                ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Use_Enderchest);
                                            }
                                        }else if (block.equals(Material.OAK_FENCE_GATE) || (block.equals(Material.ACACIA_FENCE_GATE) || (block.equals(Material.BIRCH_FENCE_GATE) || (block.equals(Material.DARK_OAK_FENCE_GATE) || (block.equals(Material.CRIMSON_FENCE_GATE) || block.equals(Material.JUNGLE_FENCE_GATE) || (block.equals(Material.SPRUCE_FENCE_GATE) || (block.equals(Material.WARPED_FENCE_GATE)))))))){
                                            if (!(points_player >= Kingdom.instance.kgetPointsConfig().Use_Fence_Gate)) {
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                                e.setCancelled(true);
                                            } else {
                                                ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Use_Fence_Gate);
                                            }
                                        }else if (block.equals(Material.OAK_TRAPDOOR) || (block.equals(Material.ACACIA_TRAPDOOR) ||
                                                (block.equals(Material.BIRCH_TRAPDOOR) || (block.equals(Material.DARK_OAK_TRAPDOOR) || (block.equals(Material.CRIMSON_TRAPDOOR)
                                                || block.equals(Material.JUNGLE_TRAPDOOR) || (block.equals(Material.SPRUCE_TRAPDOOR) || (block.equals(Material.WARPED_TRAPDOOR)))))))){
                                            if (!(points_player >= Kingdom.instance.kgetPointsConfig().Use_Trapdoor)) {
                                                player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                                e.setCancelled(true);
                                            } else {
                                                ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Use_Trapdoor);
                                            }
                                        }
                                    }
                                }
                            }
                        }else{
                            if (block.equals(Material.OAK_TRAPDOOR) || (block.equals(Material.ACACIA_TRAPDOOR) || (block.equals(Material.BIRCH_TRAPDOOR) || (block.equals(Material.DARK_OAK_TRAPDOOR) || (block.equals(Material.CRIMSON_TRAPDOOR) || block.equals(Material.JUNGLE_TRAPDOOR) || (block.equals(Material.SPRUCE_TRAPDOOR) || (block.equals(Material.WARPED_TRAPDOOR))
                                    || block.equals(Material.OAK_FENCE_GATE) || (block.equals(Material.ACACIA_FENCE_GATE) || (block.equals(Material.BIRCH_FENCE_GATE) || (block.equals(Material.DARK_OAK_FENCE_GATE) || (block.equals(Material.CRIMSON_FENCE_GATE) || block.equals(Material.JUNGLE_FENCE_GATE) || (block.equals(Material.SPRUCE_FENCE_GATE) || (block.equals(Material.WARPED_FENCE_GATE))
                                    || block.equals(Material.OAK_DOOR) || (block.equals(Material.ACACIA_DOOR) || (block.equals(Material.BIRCH_DOOR) || (block.equals(Material.DARK_OAK_DOOR) || (block.equals(Material.CRIMSON_DOOR) || block.equals(Material.JUNGLE_DOOR) || (block.equals(Material.SPRUCE_DOOR) || (block.equals(Material.WARPED_DOOR))
                                    || block.equals(Material.ENDER_CHEST) || block.equals(Material.TRAPPED_CHEST) || block.equals(Material.CHEST))))))))))))))))){
                                if (!player.hasPermission("kingdom.pointsbypass")) {
                                    player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void onBucketFill(PlayerBucketFillEvent e){
        if (Kingdom.instance.kgetPointsConfig().Place_Bucket != 0) {
            final Player player = e.getPlayer();
            User user = Kingdom.instance.kgetUserManager().getUser(player);

            if (ExtraMethods.getRegion(e.getBlockClicked().getLocation()) != null) {

                if (!Points.pointsbypass.contains(player)) {

                    final String kingdomDisplayPlayer = user.getKingdom_Display();
                    final int points_player = user.getPoints();
                    final String region_block = String.valueOf(ExtraMethods.getRegion(e.getBlockClicked().getLocation()));

                    if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)) {

                        if (region_block.equalsIgnoreCase(Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "region"))) {

                            final String kingdomofregion = Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "kingdom");
                            Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomofregion.toLowerCase());

                            //CHECKS IF THE REGION EQUALS THE KINGDOM OF THE PLAYER.
                            if (!kingdomDisplayPlayer.equalsIgnoreCase(kingdoms.getKingdomDisplay())) {

                                //CHECKS FOR ALLY BYPASS.
                                if (!allyCheck(kingdomofregion, user.getKingdom().toLowerCase(), e.getBlockClicked().getLocation())) {


                                    if (!(points_player >= Kingdom.instance.kgetPointsConfig().Place_Bucket)) {
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                        e.setCancelled(true);
                                    } else {
                                        ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Place_Bucket);
                                    }
                                }
                            }
                        }
                    }else {
                        if (!player.hasPermission("kingdom.pointsbypass")) {
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void onBucketEmpty(PlayerBucketEmptyEvent e){
        if (Kingdom.instance.kgetPointsConfig().Place_Bucket != 0) {
            final Player player = e.getPlayer();
            User user = Kingdom.instance.kgetUserManager().getUser(player);

            if (ExtraMethods.getRegion(e.getBlockClicked().getLocation()) != null) {

                if (!Points.pointsbypass.contains(player)) {

                    final String kingdomDisplayPlayer = user.getKingdom_Display();
                    final int points_player = user.getPoints();
                    final String region_block = String.valueOf(ExtraMethods.getRegion(e.getBlockClicked().getLocation()));

                    if (!user.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)) {

                        if (region_block.equalsIgnoreCase(Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "region"))) {

                            final String kingdomofregion = Kingdom.instance.kgetKingdomsMethods().getTheKingdomVIAregion(region_block, "kingdom");
                            Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(kingdomofregion.toLowerCase());

                            //CHECKS IF THE REGION EQUALS THE KINGDOM OF THE PLAYER.
                            if (!kingdomDisplayPlayer.equalsIgnoreCase(kingdoms.getKingdomDisplay())) {

                                //CHECKS FOR ALLY BYPASS.
                                if (!allyCheck(kingdomofregion, user.getKingdom().toLowerCase(), e.getBlockClicked().getLocation())) {


                                    if (!(points_player >= Kingdom.instance.kgetPointsConfig().Place_Bucket)) {
                                        player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Points_Not_Enough);
                                        e.setCancelled(true);
                                    } else {
                                        ExtraMethods.RemovePlayerPoints(player, Kingdom.instance.kgetPointsConfig().Place_Bucket);
                                    }
                                }
                            }
                        }
                    }else {
                        if (!player.hasPermission("kingdom.pointsbypass")) {
                            player.sendMessage(Kingdom.instance.kGetConfig().Prefix + Kingdom.instance.kgetMessages().Have_To_Be_Kingdom_Member);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

}
