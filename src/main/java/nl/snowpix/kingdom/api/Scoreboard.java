package nl.snowpix.kingdom.api;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public class Scoreboard {

    private static final char[] CHARS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', 'a', 'b', 'c', 'd', 'e', 'f', 'l'};

    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private final Objective objective;
    private final Player player;
    private int index = 0;

    public Scoreboard(String title, Player player)
    {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = this.scoreboard.registerNewObjective(String.valueOf(player.getEntityId()), "dummy", (title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        this.objective = objective;
    }

    public void add(String identifier, String prefixsuffix, Player player1)
    {
        this.add(identifier, prefixsuffix, player1, true);
    }

    public void add(String identifier, String prefixsuffix, Player player1, boolean space)
    {
        Team team = this.scoreboard.registerNewTeam(identifier);
        team.addEntry(("§" + CHARS[this.index]));

        prefixsuffix = ChatColor.translateAlternateColorCodes('&', prefixsuffix);
        String alternate = PlaceholderAPI.setPlaceholders(player1, prefixsuffix);
        String pre = getFirstSplit(alternate);
        String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(alternate));

        team.setPrefix(pre);
        team.setSuffix(suf);

        this.objective.getScore(("§" + CHARS[this.index])).setScore(Integer.parseInt(identifier));
        this.index++;
    }

    public void apply()
    {
        this.player.setScoreboard(this.scoreboard);
    }

    public static void update(Player player, String identifier, String prefixsuffix)
    {
        Team team = player.getScoreboard().getTeam(identifier);

        if(team == null) {
            return;
        }

        prefixsuffix = ChatColor.translateAlternateColorCodes('&', prefixsuffix);
        String alternate = PlaceholderAPI.setPlaceholders(player, prefixsuffix);
        String pre = getFirstSplit(alternate);
        String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(alternate));

        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public static void delete(Player player){
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public static String getFirstSplit(String s) {
        return s.length()>16 ? s.substring(0, 16) : s;
    }

    public static String getSecondSplit(String s) {
        if(s.length()>32) {
            s = s.substring(0, 32);
        }
        return s.length()>16 ? s.substring(16) : "";
    }
}
