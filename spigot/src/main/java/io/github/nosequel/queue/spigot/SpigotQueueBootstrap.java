package io.github.nosequel.queue.spigot;

import io.github.nosequel.command.bukkit.BukkitCommandHandler;
import io.github.nosequel.queue.shared.QueueBootstrap;
import io.github.nosequel.queue.shared.config.ServerConfiguration;
import io.github.nosequel.queue.shared.model.server.ServerModel;
import io.github.nosequel.queue.spigot.listener.PlayerListener;
import io.github.nosequel.queue.spigot.logger.SpigotQueueLogger;
import io.github.nosequel.queue.spigot.provider.SpigotPlayerProvider;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SpigotQueueBootstrap extends QueueBootstrap {

    private final JavaPlugin plugin;

    public SpigotQueueBootstrap(File dataFolder, JavaPlugin plugin) {
        super(dataFolder);
        this.plugin = plugin;
    }

    @Override
    public void load() {
        // setup logger
        new SpigotQueueLogger(true, true);

        super.load();

        // setup the player provider for the player
        this.playerModelHandler.setPlayerProvider(new SpigotPlayerProvider());

        // setup the local server subscriber
        final ServerModel serverModel = ServerConfiguration.LOCAL_SERVER.createModel(this.serverHandler);

        serverModel.setupSubscriber(this.queueHandler.getProvider(), playerModelHandler);
        this.serverHandler.setLocalServer(serverModel);

        // register listeners
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(this.playerModelHandler, this.serverHandler), plugin);

        // register commands
        this.registerCommands(new BukkitCommandHandler("queue"));

        // set server max players
        serverModel.setMaxPlayers(Bukkit.getMaxPlayers());
        serverModel.updateToStorage(this.serverHandler.getProvider());
    }
}