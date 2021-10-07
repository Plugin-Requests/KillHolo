package net.savagedev.killholo.listeners;

import net.savagedev.killholo.KillHolo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DeathListener implements Listener {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final KillHolo killHolo;

    public DeathListener(KillHolo killHolo) {
        this.killHolo = killHolo;
    }

    @EventHandler
    public void on(PlayerDeathEvent event) { // Did a player die?
        final Player dead = event.getEntity();
        final Player killer = dead.getKiller(); // Whomst killed said player?

        if (killer == null) { // Make sure the player was killed by another player.
            return;
        }

        this.killHolo.getHologramProvider().create(dead.getLocation(),
                this.killHolo.getConfig().getLong("hologram-expire"),
                this.getRandomHologramText(dead, killer)
        );
    }

    private List<String> getRandomHologramText(Player dead, Player killer) {
        final List<String> hologramNames = new ArrayList<>(this.killHolo.getConfig().getConfigurationSection("holograms").getKeys(false));
        return this.killHolo.getConfig()
                .getStringList("holograms." + hologramNames.get(this.random.nextInt(hologramNames.size())))
                .stream()
                .map(line -> {
                    line = line.replace("%dead%", dead.getDisplayName());
                    line = line.replace("%killer%", killer.getDisplayName());
                    return line;
                })
                .collect(Collectors.toList());
    }
}
