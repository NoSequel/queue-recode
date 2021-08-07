package io.github.nosequel.queue.spigot.provider;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import io.github.nosequel.command.executor.CommandExecutor;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerProvider;
import io.github.nosequel.queue.spigot.SpigotQueuePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayerProvider implements PlayerProvider {

    /**
     * Get the unique identifier of a {@link CommandExecutor}
     *
     * @param executor the executor to get the unique identifier of
     * @return the unique identifier, or null
     */
    @Override
    public UUID getUniqueId(CommandExecutor executor) {
        if (executor instanceof BukkitCommandExecutor) {
            return executor.isUser()
                    ? ((BukkitCommandExecutor) executor).getPlayer().getUniqueId()
                    : null;
        }

        return null;
    }

    /**
     * Send a message to a {@link PlayerModel}
     *
     * @param playerModel the player model to send a message
     * @param message     the message to send to the player
     */
    @Override
    public void sendMessage(PlayerModel playerModel, String message) {
        final Player player = Bukkit.getPlayer(playerModel.getUniqueId());

        if (player != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    /**
     * Send a {@link PlayerModel} to a server
     *
     * @param playerModel the player model to send to the server
     * @param serverName  the server to send the player to
     */
    @Override
    public void sendToServer(PlayerModel playerModel, String serverName) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(serverName);

        final Player player = Bukkit.getPlayer(playerModel.getUniqueId());

        player.sendPluginMessage(SpigotQueuePlugin.getPlugin(SpigotQueuePlugin.class), "BungeeCord", out.toByteArray());
    }
}
