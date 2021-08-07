package io.github.nosequel.queue.client;

import io.github.nosequel.queue.client.logger.TestClientLogger;
import io.github.nosequel.queue.shared.QueueBootstrap;
import io.github.nosequel.queue.shared.config.ServerConfiguration;
import io.github.nosequel.queue.shared.logger.QueueLogger;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

public class QueueClientBootstrap extends QueueBootstrap {

    @Override
    @SneakyThrows
    public void load() {
        new TestClientLogger(true, true);

        super.load();

        ServerConfiguration.LOCAL_SERVER.createModel(this.serverHandler).setupSubscriber(this.queueHandler.getProvider(), playerModelHandler);

        while (true) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            final String[] command = reader.readLine().split(" ");

            if (command.length != 3) {
                QueueLogger.getInstance().warn("Must provide 2 arguments.");
                continue;
            }

            final QueueModel queueModel = this.queueHandler.fetchModel(command[1]);

            if (command[0].equalsIgnoreCase("addmodel")) {
                final String modelName = command[1];
                final PlayerModel model = this.playerModelHandler.createModel(UUID.randomUUID(), modelName);

                queueModel.addEntry(model, this.queueHandler.getProvider());

                QueueLogger.getInstance().log("Adding new player model to queue..");
            }
        }
    }
}