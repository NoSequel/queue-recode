package io.github.nosequel.queue.spigot.listener;

import io.github.nosequel.queue.shared.config.ServerConfiguration;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerModelHandler;
import io.github.nosequel.queue.shared.model.server.ServerHandler;
import io.github.nosequel.queue.shared.model.server.ServerModel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final PlayerModelHandler playerHandler;
    private final ServerHandler serverHandler;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final ServerModel localServer = this.serverHandler.getLocalServer();

        final PlayerModel model = this.playerHandler.fetchModel(player.getUniqueId().toString());

        if (model == null) {
            this.playerHandler.createModel(player.getUniqueId(), player.getName());
        }


        localServer.setOnlinePlayers(Bukkit.getOnlinePlayers().size());
        localServer.updateToStorage(this.serverHandler.getProvider());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final ServerModel localServer = this.serverHandler.getLocalServer();

        localServer.setOnlinePlayers(Bukkit.getOnlinePlayers().size());
        localServer.updateToStorage(this.serverHandler.getProvider());
    }
}