package io.github.nosequel.queue.proxy.task;

import io.github.nosequel.queue.shared.logger.QueueLogger;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerModelHandler;
import io.github.nosequel.queue.shared.model.queue.QueueHandler;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class QueueMoveTask {

    private final QueueHandler queueHandler;
    private final PlayerModelHandler playerModelHandler;

    private boolean running = true;

    @SneakyThrows
    public QueueMoveTask(QueueHandler queueHandler, PlayerModelHandler playerModelHandler) {
        this.queueHandler = queueHandler;
        this.playerModelHandler = playerModelHandler;

        final Set<QueueModel> models = queueHandler.fetchModels();

        for (QueueModel model : models) {
            if (model.getEntries().isEmpty()) {
                model.addEntry(playerModelHandler.createModel(UUID.randomUUID(), UUID.randomUUID().toString()), queueHandler.getProvider());
            }
        }

        this.run();
    }

    @SneakyThrows
    public void run() {
        while (running) {
            for (QueueModel model : this.queueHandler.fetchModels()) {
                if (!model.getEntries().isEmpty()) {
                    final PlayerModel playerModel = model.getEntries().poll();

                    if (playerModel != null) {
                        model.updateToStorage(this.queueHandler.getProvider());
                        model.sendMoveUpdate(playerModel, this.queueHandler.getProvider());

                        QueueLogger.getInstance().log("Moving " + playerModel.getUniqueId() + " out of the " + model.getIdentifier() + " queue.");
                    }
                } else {
                    QueueLogger.getInstance().debug("Skipping " + model.getIdentifier());
                }
            }

            Thread.sleep(1500);
        }
    }
}