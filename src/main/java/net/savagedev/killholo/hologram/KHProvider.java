package net.savagedev.killholo.hologram;

import net.savagedev.killholo.KillHolo;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KHProvider implements HologramProvider {
    private final List<KillHologram> holograms = new ArrayList<>();

    private final KillHolo killHolo;

    public KHProvider(KillHolo killHolo) {
        this.killHolo = killHolo;
    }

    @Override
    public void create(Location location, long expireAfter, List<String> lines) {
        final KillHologram hologram = new KillHologram(location);
        this.holograms.add(hologram);
        for (String line : lines) {
            hologram.append(ChatColor.translateAlternateColorCodes('&', line));
        }
        this.killHolo.getServer().getScheduler().runTaskLater(this.killHolo, hologram::remove, expireAfter);
    }

    @Override
    public void deleteAll() {
        this.holograms.forEach(KillHologram::remove);
        this.holograms.clear();
    }

    private class KillHologram {
        private final List<ArmorStand> armorStands = new LinkedList<>();

        private final Location location;

        public KillHologram(Location location) {
            this.location = location;
        }

        public void append(String line) {
            final ArmorStand armorStand = this.location.getWorld().spawn(this.location.subtract(0.0d, (this.armorStands.size() * .250d), 0.0d), ArmorStand.class);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(line);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            this.armorStands.add(armorStand);
        }

        public void remove() {
            this.armorStands.forEach(ArmorStand::remove);
            KHProvider.this.holograms.remove(this);
        }
    }
}
