package net.savagedev.killholo;

import net.savagedev.killholo.hologram.HDProvider;
import net.savagedev.killholo.hologram.HologramProvider;
import net.savagedev.killholo.hologram.KHProvider;
import net.savagedev.killholo.listeners.DeathListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class KillHolo extends JavaPlugin {
    private HologramProvider hologramProvider;

    @Override
    public void onEnable() {
        // Basic stuff... Create & load config, & initialize listeners.
        this.saveDefaultConfig();
        this.initListeners();
        // Initialize the hologram provider.
        this.initHologramProvider();
        // Just in case the server crashed, clean up any stray holograms that got left around. (Only works for holograms created by HolographicDisplays)
        this.hologramProvider.deleteAll();
    }

    @Override
    public void onDisable() {
        // Just to be sure there won't be any stray holograms around when the server restarts.
        // A little redundant, but it means less work for the server on startup, if the server shuts down correctly.
        this.hologramProvider.deleteAll();
    }

    private void initHologramProvider() {
        if (this.getServer().getPluginManager().isPluginEnabled("HolographicDisplays")) {
            this.hologramProvider = new HDProvider(this);
        } else {
            this.getLogger().log(Level.WARNING, "HolographicDisplays not detected! " +
                    "It is recommended that you install HolographicDisplays, as my hologram implementation will more than likely contain bugs.");
            this.hologramProvider = new KHProvider(this);
        }
    }

    private void initListeners() {
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new DeathListener(this), this);
    }

    public HologramProvider getHologramProvider() {
        return this.hologramProvider;
    }
}
