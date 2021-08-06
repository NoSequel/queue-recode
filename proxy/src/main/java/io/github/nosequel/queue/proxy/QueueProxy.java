package io.github.nosequel.queue.proxy;

import io.github.nosequel.config.json.JsonConfigurationFile;
import io.github.nosequel.queue.proxy.config.GlobalConfig;
import io.github.nosequel.queue.proxy.config.QueueConfig;
import io.github.nosequel.queue.proxy.config.model.QueueModelConfig;
import io.github.nosequel.queue.proxy.logger.ProxyLogger;
import io.github.nosequel.queue.proxy.task.QueueMoveTask;
import io.github.nosequel.queue.shared.model.queue.QueueHandler;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import io.github.nosequel.storage.redis.RedisStorageHandler;
import io.github.nosequel.storage.redis.settings.impl.NoAuthRedisSettings;
import io.github.nosequel.storage.storage.impl.RedisStorageProvider;

import java.io.File;

public class QueueProxy {

    public static void main(String[] args) {
        new QueueConfig(new JsonConfigurationFile(new File("queues.json")));
        new GlobalConfig(new JsonConfigurationFile(new File("config.json")));

        // initialize logger
        new ProxyLogger(GlobalConfig.LOG_DEBUG, GlobalConfig.LOG_WARNINGS);

        // redis stuff
        final RedisStorageHandler storageHandler = new RedisStorageHandler(new NoAuthRedisSettings("panel.clox.us", 6379));
        final RedisStorageProvider<QueueModel> provider = new RedisStorageProvider<>("queues", storageHandler, QueueModel.class);

        // the queue handler itself
        final QueueHandler queueHandler = new QueueHandler(provider);

        // setup the configuration
        for (QueueModelConfig model : QueueConfig.MODELS) {
            queueHandler.createQueue(new QueueModel(provider, model.getIdentifier()));
        }

        // start the move task
        new QueueMoveTask(queueHandler);
    }

}
