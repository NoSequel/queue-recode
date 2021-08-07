package io.github.nosequel.queue.shared.model.queue;

import io.github.nosequel.queue.shared.model.ModelHandler;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.SneakyThrows;

import java.util.*;

public class QueueHandler extends ModelHandler<QueueModel> {


    public QueueHandler(StorageProvider<String, QueueModel> provider) {
        super(provider);
    }

    /**
     * Create a new {@link QueueModel} object.
     * <p>
     * This method writes the queue to the provided
     * storage provider within the queue handler object.
     * </p>
     *
     * @param model the model to create/register
     */
    public void createQueue(QueueModel model) {
        this.provider.setEntry(model.getIdentifier(), model);
    }

    /**
     * Get all queues a {@link PlayerModel} is in.
     *
     * @param uniqueId the model to get the queues from
     * @return the set of the queues
     */
    @SneakyThrows
    public Set<QueueModel> getQueues(UUID uniqueId) {
        final Map<String, QueueModel> map = this.provider.fetchAllEntries().join();
        final Set<QueueModel> models = new HashSet<>();

        for (QueueModel value : map.values()) {
            if (value.getEntries().stream().anyMatch(entry -> entry.getUniqueId().equals(uniqueId))) {
                models.add(value);
            }
        }

        return models;
    }

    /**
     * Create a new model with provided data.
     *
     * @param data the data to use to instantiate the model
     * @return the newly created model
     */
    @Override
    public QueueModel createModel(Object... data) {
        return new QueueModel(
                this.provider,
                (String) data[0]
        );
    }
}