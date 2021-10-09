package nl.snowpix.kingdom.api;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderHook extends PlaceholderExpansion {

    @Override
    public boolean persist()
    {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "KD";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Pottenmaker";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.3";
    }


    public String onPlaceholderRequest(Player player, String identifier) {

        if(identifier.equalsIgnoreCase("online")){
            return String.valueOf(Bukkit.getOnlinePlayers().size());
        }


        if(player == null){
            return "";
        }

        if(identifier.equalsIgnoreCase("name")){
            return player.getName();
        }

        User user = Kingdom.instance.kgetUserManager().getUser(player);

        if (identifier.equalsIgnoreCase("kingdom")){
            return user.getKingdom();
        }

        if (identifier.equalsIgnoreCase("kingdomdisplay")){
            return user.getKingdom_Display();
        }

        if (identifier.equalsIgnoreCase("kingdomprefix")){
            return user.getKingdom_Prefix();
        }

        if (identifier.equalsIgnoreCase("tab")){
            return user.getTab();
        }

        if (identifier.equalsIgnoreCase("rank")){
            return user.getRank();
        }

        if (identifier.equalsIgnoreCase("rankprefix")){
            return user.getRank_Prefix();
        }

        if (identifier.equalsIgnoreCase("rankdisplay")){
            return user.getRank_Display();
        }


        if (identifier.equalsIgnoreCase("soort")){
            return user.getSoort();
        }

        if (identifier.equalsIgnoreCase("points")){
            return String.valueOf(user.getPoints());
        }

        if (identifier.equalsIgnoreCase("influence")){
            return String.valueOf(user.getPoints());
        }

        if (identifier.equalsIgnoreCase("locatie")){
            if (!PlaceholderAPI.setPlaceholders(player, "%worldguard_region_name_capitalized%").equalsIgnoreCase("")){
                return PlaceholderAPI.setPlaceholders(player, "%worldguard_region_name_capitalized%");
            }else{
                return Kingdom.instance.kGetConfig().Wilderniss_Name;
            }
        }



        return null;
    }





}
