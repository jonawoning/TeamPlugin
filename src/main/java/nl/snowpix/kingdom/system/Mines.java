package nl.snowpix.kingdom.system;

import nl.snowpix.kingdom.events.BlockBreakMine;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mines {

    public static YamlConfiguration cfg_mines = YamlConfiguration.loadConfiguration(new File("plugins//Kingdom//", "MineIslands.yml"));
    public static File mines = new File("plugins//Kingdom//MineIslands.yml");

    public void checkMines(){
        if (mines.exists()){

            cfg_mines.set("RegionMines", BlockBreakMine.configRegions);
            try {
                cfg_mines.save(mines);
            } catch (IOException e){
                e.printStackTrace();
            }

        }else{
            List<String> test = new ArrayList<>();
            test.add("mineisland");
            cfg_mines.set("RegionMines", test);
            try {
                cfg_mines.save(mines);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
