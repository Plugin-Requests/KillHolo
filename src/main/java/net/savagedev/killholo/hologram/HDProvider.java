package net.savagedev.killholo.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.savagedev.killholo.KillHolo;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.List;

public class HDProvider implements HologramProvider {
    private final KillHolo killHolo;

    public HDProvider(KillHolo killHolo) {
        this.killHolo = killHolo;
    }

    @Override
    public void create(Location location, long expireAfter, List<String> lines) {
        final Hologram hologram = HologramsAPI.createHologram(this.killHolo, location.add(0.0d, 2.0d, 0.0d));
        for (String line : lines) {
            hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', line));
        }
        this.killHolo.getServer().getScheduler().runTaskLater(this.killHolo, hologram::delete, expireAfter);
    }

    @Override
    public void deleteAll() {
        HologramsAPI.getHolograms(this.killHolo).forEach(Hologram::delete);
    }
}
