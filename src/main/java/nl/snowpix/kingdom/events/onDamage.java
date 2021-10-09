package nl.snowpix.kingdom.events;

import nl.snowpix.kingdom.data.Kingdoms;
import nl.snowpix.kingdom.data.User;
import nl.snowpix.kingdom.Kingdom;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onDamage implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onPlayerDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            if (Kingdom.instance.kGetConfig().staffmode.contains(e.getEntity())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageByEntityEvent event) {
        final Entity damaged = event.getEntity();
        final Entity damager = event.getDamager();
        if (event.getEntity() instanceof Player) {
            final User userdamaged = Kingdom.instance.kgetUserManager().getUser((Player)damaged);
            if (event.getDamager() instanceof Player) {
                final User userdamager = Kingdom.instance.kgetUserManager().getUser((Player)damager);
                if (userdamager.getKingdom() == null) {
                    event.setCancelled(true);
                    return;
                }
                if (userdamager.getKingdom() != null && userdamaged.getKingdom() != null) {
                    if (userdamager.getKingdom().equalsIgnoreCase(userdamaged.getKingdom()) || allyCheck(userdamager.getKingdom(), userdamaged.getKingdom())) {
                        event.setCancelled(true);
                        event.setDamage(0.0);
                    }
                }
            }
            else if (event.getDamager() instanceof Projectile) {
                final Projectile projectile = (Projectile)damager;
                if ( projectile.getShooter() instanceof Player) {
                    final User shooter = Kingdom.instance.kgetUserManager().getUser((Player)projectile.getShooter());
                    if (shooter.getKingdom() != null && userdamaged.getKingdom() != null) {
                        if (shooter.getKingdom().equalsIgnoreCase(userdamaged.getKingdom()) || allyCheck(shooter.getKingdom(), userdamaged.getKingdom())) {
                            event.setCancelled(true);
                            event.setDamage(0.0);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onPlayerFlame(EntityCombustByEntityEvent e){
        if (e.getEntity() instanceof Player){
            final Player damaged = ((Player)e.getEntity());
            if (e.getCombuster() instanceof Arrow){
                if (((Arrow) e.getCombuster()).getShooter() instanceof Player){
                    final Player damager = ((Player)((Arrow) e.getCombuster()).getShooter());
                    final User user = Kingdom.instance.kgetUserManager().getUser(damaged);
                    final User user1 = Kingdom.instance.kgetUserManager().getUser(damager);
                    if (user1.getKingdom().equalsIgnoreCase(user.getKingdom()) || allyCheck(user.getKingdom(), user1.getKingdom())){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void OnPlayerDeath(PlayerDeathEvent e){
        if (e.getEntity() instanceof Player){
            final Player victim = e.getEntity().getPlayer();
            final User u_victim = Kingdom.instance.kgetUserManager().getUser(victim);
            Kingdoms kingdoms = Kingdom.instance.kgetKingdomManager().getKingdom(u_victim.getKingdom().toLowerCase());
            if (kingdoms.getSpawn() != null){
                victim.teleport(kingdoms.getSpawn());
            }
            if (e.getEntity().getKiller() instanceof Player){
                final Player attacker = e.getEntity().getKiller().getPlayer();
                final User u_attacker = Kingdom.instance.kgetUserManager().getUser(attacker);
                e.setDeathMessage(Kingdom.instance.kGetConfig().DeathMessage.
                        replace("%victim%", victim.getName())
                        .replace("%attacker%", attacker.getName())
                        .replace("%kingdomprefix_victim%", u_victim.getKingdom_Prefix())
                        .replace("%kingdomdisplay_victim%", u_victim.getKingdom_Display())
                        .replace("%rankdisplay_victim%", u_victim.getRank_Display())
                        .replace("%rankprefix_victim%", u_victim.getRank_Prefix())
                        .replace("%kingdomprefix_attacker%", u_attacker.getKingdom_Prefix())
                        .replace("%kingdomdisplay_attacker%", u_attacker.getKingdom_Display())
                        .replace("%rankdisplay_attacker%", u_attacker.getRank_Display())
                        .replace("%rankprefix_attacker%", u_attacker.getRank_Prefix()));
                if (!u_attacker.getKingdom().equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
                    Kingdom.instance.kgetKingdomsMethods().AddaKingdomKill(u_attacker.getKingdom().toLowerCase());
                }
            }else{
                e.setDeathMessage(Kingdom.instance.kGetConfig().DeathMessage_Other.
                        replace("%victim%", victim.getName())
                        .replace("%kingdomprefix_victim%", u_victim.getKingdom_Prefix())
                        .replace("%kingdomdisplay_victim%", u_victim.getKingdom_Display())
                        .replace("%rankdisplay_victim%", u_victim.getRank_Display())
                        .replace("%rankprefix_victim%", u_victim.getRank_Prefix()));
            }
        }
    }

    public boolean allyCheck(String kingdom1, String kingdom2){
        Kingdoms kingdoms1 = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom1.toLowerCase());
        Kingdoms kingdoms2 = Kingdom.instance.kgetKingdomManager().getKingdom(kingdom2.toLowerCase());
        if (kingdoms1.getAllies() != null && !kingdom1.equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name) && kingdoms2.getAllies() != null && !kingdom2.equalsIgnoreCase(Kingdom.instance.kGetConfig().NoKingdom_Name)){
            for (String loopkd : kingdoms1.getAllies()){
                if (loopkd.equalsIgnoreCase(kingdoms2.getKingdomName())){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

}
