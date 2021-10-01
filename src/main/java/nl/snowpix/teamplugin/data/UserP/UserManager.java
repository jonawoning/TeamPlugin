package nl.snowpix.teamplugin.data.UserP;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserManager {

    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public User getUser(Player player) {
        return users.stream().filter(user -> user.getUUID().equals(player.getUniqueId())).findAny().orElse(null);
    }

    public User getUser(UUID uuid) {
        return users.stream().filter(user -> user.getUUID().equals(uuid)).findAny().orElse(null);
    }

    public User getUser(String name) {
        return users.stream().filter(user -> user.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public void addUser(Player player) {
        users.add(new User(player));
    }

    public void removeUser(Player player) {
        users.remove(getUser(player));
    }

}
