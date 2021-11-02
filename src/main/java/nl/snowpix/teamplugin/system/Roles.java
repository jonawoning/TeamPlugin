package nl.snowpix.teamplugin.system;

import nl.snowpix.teamplugin.TeamPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Roles {

    public ArrayList<String> ALL_ROLES = new ArrayList();

    public YamlConfiguration cfg_ranksfile = YamlConfiguration.loadConfiguration(new File("plugins//TeamPlugin", "roles.yml"));
    public File ranksfile = new File("plugins//TeamPlugin//roles.yml");

    public void CheckRoles(){
        ALL_ROLES.clear();
        if (!ranksfile.exists()){
            ArrayList<String> LeaderPerms = new ArrayList();
            ArrayList<String> CoLeaderPerms = new ArrayList();
            ArrayList<String> MemberPerms = new ArrayList();
            LeaderPerms.add("teams.leader");
            LeaderPerms.add("teams.kick");
            LeaderPerms.add("teams.invite");
            LeaderPerms.add("teams.ally");
            LeaderPerms.add("teams.neutral");
            LeaderPerms.add("teams.accept");
            LeaderPerms.add("teams.spawn");
            LeaderPerms.add("teams.setspawn");
            LeaderPerms.add("teams.leave");
            CoLeaderPerms.add("teams.leader");
            CoLeaderPerms.add("teams.ally");
            CoLeaderPerms.add("teams.neutral");
            CoLeaderPerms.add("teams.invite");
            CoLeaderPerms.add("teams.spawn");
            CoLeaderPerms.add("teams.leave");
            MemberPerms.add("teams.spawn");
            MemberPerms.add("teams.leave");
            cfg_ranksfile.set("Roles.Leader.Prefix", "&7[&6Leader&7]");
            cfg_ranksfile.set("Roles.Leader.Display", "&6Leader");
            cfg_ranksfile.set("Roles.Leader.Weight", 3);
            cfg_ranksfile.set("Roles.Leader.Permissions", LeaderPerms);
            cfg_ranksfile.set("Roles.CoLeader.Prefix", "&7[&2CoLeader&7]");
            cfg_ranksfile.set("Roles.CoLeader.Display", "&2CoLeader");
            cfg_ranksfile.set("Roles.CoLeader.Weight", 2);
            cfg_ranksfile.set("Roles.CoLeader.Permissions", CoLeaderPerms);
            cfg_ranksfile.set("Roles.Member.Prefix", "&7[&8Member&7]");
            cfg_ranksfile.set("Roles.Member.Display", "&8Member");
            cfg_ranksfile.set("Roles.Member.Weight", 1);
            cfg_ranksfile.set("Roles.Member.Permissions", MemberPerms);
            ALL_ROLES.add("Leader");
            ALL_ROLES.add("CoLeader");
            ALL_ROLES.add("Member");
            try {
                cfg_ranksfile.save(ranksfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Set<String> ranks = cfg_ranksfile.getConfigurationSection("Roles").getKeys(false);
            if (!ranks.contains(TeamPlugin.instance.kgetConfig().Member_Rank)){
                System.out.println(SystemColors.TEXT_YELLOW + "[TeamPlugin] " + SystemColors.TEXT_RED + "THE MEMBER RANK OF THE CONFIG CAN NOT BE FOUND! THIS CAN BE CASE SENSITIVE!");
                System.out.println(SystemColors.TEXT_YELLOW + "[TeamPlugin] " + SystemColors.TEXT_RED + "The Rank that needs to be in the ranks.yml: '" + TeamPlugin.instance.kgetConfig().Member_Rank + "'");
            }
            for (String test : ranks){
                ALL_ROLES.add(test);
            }
        }
    }

    public boolean checkRank(String rankname){
        return ALL_ROLES.contains(rankname);
    }

}
