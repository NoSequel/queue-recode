package io.github.nosequel.queue.shared.model.server;

import io.github.nosequel.queue.shared.logger.QueueLogger;
import io.github.nosequel.queue.shared.model.Model;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerModelHandler;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;

import java.util.Arrays;
import java.util.UUID;

@Getter
public class ServerModel extends Model<ServerModel> {

    public ServerModel(StorageProvider<String, ServerModel> storageProvider, String identifier) {
        super(storageProvider, identifier);
    }

    /**
     * Setup the subscriber used for incoming messaging along the server's channel
     *
     * @param storageProvider the storage provider
     * @param playerHandler   the player handler
     */
    public void setupSubscriber(StorageProvider<String, QueueModel> storageProvider, PlayerModelHandler playerHandler) {
        if (this.identifier == null) {
            throw new IllegalStateException("Unable to setup subscriber update whilst #identifier is null");
        }

        storageProvider.getStorageHandler().subscribe(this.identifier + "-move", message -> {
            final String[] splitMessage = message.split("\\|\\|");

            final UUID uniqueId = UUID.fromString(splitMessage[0]);
            final String serverId = splitMessage[1];

            final PlayerModel playerModel = playerHandler.fetchModel(uniqueId.toString());

            if (playerModel != null) {
                QueueLogger.getInstance().debug("Attempting to send player to server");

                if (playerHandler.getPlayerProvider() != null) {
                    playerHandler.getPlayerProvider().sendToServer(playerModel, serverId);
                }
            }
        });
    }
}