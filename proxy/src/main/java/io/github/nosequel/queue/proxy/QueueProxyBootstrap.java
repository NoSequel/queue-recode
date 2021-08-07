package io.github.nosequel.queue.proxy;

import io.github.nosequel.config.json.JsonConfigurationFile;
import io.github.nosequel.queue.proxy.config.GlobalConfig;
import io.github.nosequel.queue.proxy.config.QueueConfig;
import io.github.nosequel.queue.proxy.config.model.QueueModelConfig;
import io.github.nosequel.queue.proxy.logger.ProxyLogger;
import io.github.nosequel.queue.proxy.task.QueueMoveTask;
import io.github.nosequel.queue.shared.QueueBootstrap;

import java.io.File;

public class QueueProxyBootstrap extends QueueBootstrap {

    @Override
    public void load() {
        new QueueConfig(new JsonConfigurationFile(new File("queues.json")));
        new GlobalConfig(new JsonConfigurationFile(new File("config.json")));

        // initialize logger
        new ProxyLogger(GlobalConfig.LOG_DEBUG, GlobalConfig.LOG_WARNINGS);

        super.load();

        // setup the configuration
        for (QueueModelConfig model : QueueConfig.MODELS) {
            queueHandler.createQueue(model.createModel(queueHandler.getProvider(), serverHandler));
        }

        // start the move task
        new QueueMoveTask(queueHandler, playerModelHandler);
    }
}