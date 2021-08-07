package io.github.nosequel.queue.shared.model.queue;

import io.github.nosequel.queue.shared.logger.QueueLogger;
import io.github.nosequel.queue.shared.model.Model;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.server.ServerModel;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Jedis;

import java.util.*;

@Getter
@Setter
public class QueueModel extends Model<QueueModel> {

    private final PriorityQueue<PlayerModel> entries = new PriorityQueue<>(Comparator.comparingInt(player -> -player.getPriority()));

    // the server to send the player to
    private ServerModel targetServer;

    public QueueModel(StorageProvider<String, QueueModel> storageProvider, String identifier) {
        super(storageProvider, identifier);
    }

    /**
     * Check if the queue is joinable
     *
     * @return whether the queue is joinable or not
     */
    public boolean isJoinableQueue() {
        if (this.targetServer == null) {
            return false;
        }

        return this.targetServer.getMaxPlayers() > this.targetServer.getOnlinePlayers();
    }

    /**
     * Send an update to move to the server.
     * <p>
     * This method sends a {@link Jedis#publish(String, String)} message
     * to the target server, unless the {@link QueueModel#targetServer} object is null.
     * </p>
     *
     * @param model    the model to move to the other server
     */
    public void sendMoveUpdate(PlayerModel model) {
        if (this.targetServer == null) {
            QueueLogger.getInstance().warn("Unable to send player leave update whilst #targetServer is null");
        } else {
            this.storageProvider.getStorageHandler()
                    .publish(this.targetServer.getIdentifier() + "-move", model.getUniqueId().toString() + "||" + this.targetServer.getIdentifier());
        }
    }

    /**
     * Get the current queue position of a {@link PlayerModel}
     * within a {@link QueueModel}.
     *
     * @param playerModel the player to find within the queue
     * @return the position of the player in the queue, if for some reason
     * unable to find within the queue it will return -1.
     */
    public Integer getPosition(PlayerModel playerModel) {
        if (!this.hasPlayer(playerModel)) {
            throw new IllegalArgumentException("playerModel with unique identifier " + playerModel.getUniqueId().toString() + " is not in the " + this.identifier + " queue.");
        }

        for (int i = 0; i < this.entries.size(); i++) {
            final PlayerModel current = this.entries.peek();

            if (current != null && current.getUniqueId().equals(playerModel.getUniqueId())) {
                return i + 1;
            }
        }

        return -1;
    }

    /**
     * Check if the {@link QueueModel} has a {@link PlayerModel} as entry.
     *
     * @param model the model to check for
     * @return whether it has a player
     */
    public boolean hasPlayer(PlayerModel model) {
        for (PlayerModel entry : this.entries) {
            if (entry.getUniqueId().equals(model.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add an entry to the {@link QueueModel#entries} queue.
     * <p>
     * This method updates the queue within
     * the provided {@link StorageProvider} as well.
     * </p>
     *
     * @param model the model to add to the queue
     */
    public void addEntry(PlayerModel model) {
        this.entries.add(model);

        // update the data within the global cache
        this.updateToStorage(this.storageProvider);
    }
}