package nl.snowpix.kingdom.data;


import java.util.HashSet;
import java.util.Set;

public class KingdomsManager {

    private Set<Kingdoms> kingdoms = new HashSet<>();

    public Set<Kingdoms> getKingdoms() {
        return kingdoms;
    }

    public Kingdoms getKingdom(String kingdom) {
        return kingdoms.stream().filter(kingdoms -> kingdoms.getKingdomName().equals(kingdom)).findAny().orElse(null);
    }

    public void addKingdom(String kingdom) {
        kingdoms.add(new Kingdoms(kingdom));
    }

    public void removeKingdom(String kingdom) {
        kingdoms.remove(kingdom);
    }

}
