package io.github.nosequel.queue.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotQueuePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        new SpigotQueueBootstrap(this.getDataFolder(), this).load();
    }
}