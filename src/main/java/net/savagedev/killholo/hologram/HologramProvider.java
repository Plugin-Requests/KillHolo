package net.savagedev.killholo.hologram;

import org.bukkit.Location;

import java.util.List;

public interface HologramProvider {
    void create(Location location, long expireAfter, List<String> lines);

    void deleteAll();
}
