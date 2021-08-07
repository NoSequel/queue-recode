package io.github.nosequel.queue.shared.model.player;

public interface PlayerProvider {

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
