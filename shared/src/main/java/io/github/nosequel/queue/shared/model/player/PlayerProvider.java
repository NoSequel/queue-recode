package io.github.nosequel.queue.shared.model.player;

import io.github.nosequel.command.executor.CommandExecutor;

import java.util.UUID;

public interface PlayerProvider {

    /**
     * Get the unique identifier of a {@link CommandExecutor}
     *
     * @param executor the executor to get the unique identifier of
     * @return the unique identifier, or null
     */
    UUID getUniqueId(CommandExecutor executor);

    /**
     * Send a message to a {@link PlayerModel}
     *
     * @param playerModel the player model to send a message
     * @param message     the message to send to the player
     */
    void sendMessage(PlayerModel playerModel, String message);

    /**
     * Send a {@link PlayerModel} to a server
     *
     * @param playerModel the player model to send to the server
     * @param serverName  the server to send the player to
     */
    void sendToServer(PlayerModel playerModel, String serverName);

}
