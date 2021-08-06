package io.github.nosequel.queue.shared.model.queue;

import io.github.nosequel.queue.shared.model.Model;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.server.ServerModel;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.UUID;

@Getter
@Setter
public class QueueModel extends Model<String, QueueModel> {

    private final String identifier;
    private final PriorityQueue<PlayerModel> entries = new PriorityQueue<>(Comparator.comparingInt(player -> -player.getPriority()));

    // the server to send the player to
    private ServerModel targetServer;

    public QueueModel(StorageProvider<String, QueueModel> storageProvider, String identifier) {
        super(storageProvider);
        this.identifier = identifier;
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
        if (!this.entries.contains(playerModel)) {
            throw new IllegalArgumentException("playerModel with unique identifier " + playerModel.getUniqueId().toString() + " is not in the " + this.identifier + " queue.");
        }

        for (int i = 0; i < this.entries.size(); i++) {
            final PlayerModel current = this.entries.peek();

            if (current != null && current.equals(playerModel)) {
                return i + 1;
            }
        }

        return -1;
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
    public void addEntry(PlayerModel model, StorageProvider<String, QueueModel> provider) {
        this.entries.add(model);

        // update the data within the global cache
        this.updateQueue(provider);
    }

    /**
     * Update the queue to the {@link StorageProvider}.
     *
     * @param provider the provider to update it to
     */
    public void updateQueue(StorageProvider<String, QueueModel> provider) {
        provider.setEntry(this.identifier, this);
    }

}