package nl.snowpix.teamplugin.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import nl.snowpix.teamplugin.TeamPlugin;
import nl.snowpix.teamplugin.data.UserP.User;
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
        return "TeamP";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Pottenmaker";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
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

        User user = TeamPlugin.instance.kgetUserManager().getUser(player);

        if (identifier.equalsIgnoreCase("team")){
            return user.getTeam();
        }

        if (identifier.equalsIgnoreCase("teamprefix")){
            return user.getTeam_prefix();
        }

        if (identifier.equalsIgnoreCase("role")){
            return user.getRole();
        }

        if (identifier.equalsIgnoreCase("role_prefix")){
            return user.getRolePrefix();
        }

        if (identifier.equalsIgnoreCase("role_display")){
            return user.getRoleDisplay();
        }

        if (identifier.equalsIgnoreCase("role_weight")){
            return String.valueOf(user.getRoleWeight());
        }



        return null;
    }





}
