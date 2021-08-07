package io.github.nosequel.queue.spigot.listener;

import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerModelHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final PlayerModelHandler playerHandler;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final PlayerModel model = this.playerHandler.fetchModel(player.getUniqueId().toString());

        if (model == null) {
            this.playerHandler.createModel(player.getUniqueId(), player.getName());
        }
    }
}