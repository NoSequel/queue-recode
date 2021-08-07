package io.github.nosequel.queue.shared;

import io.github.nosequel.config.json.JsonConfigurationFile;
import io.github.nosequel.queue.shared.config.ServerConfiguration;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerModelHandler;
import io.github.nosequel.queue.shared.model.queue.QueueHandler;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import io.github.nosequel.storage.redis.RedisStorageHandler;
import io.github.nosequel.storage.settings.settings.impl.NoAuthRedisSettings;
import io.github.nosequel.storage.storage.impl.RedisStorageProvider;
import lombok.Getter;

import java.io.File;

@Getter
public abstract class QueueBootstrap {

    protected QueueHandler queueHandler;
    protected PlayerModelHandler playerModelHandler;

    public void load() {
        // redis stuff
        final RedisStorageHandler storageHandler = new RedisStorageHandler(new NoAuthRedisSettings("panel.clox.us", 6379));

        final RedisStorageProvider<PlayerModel> playerProvider = new RedisStorageProvider<>("players", storageHandler, PlayerModel.class);
        final RedisStorageProvider<QueueModel> queueProvider = new RedisStorageProvider<>("queues", storageHandler, QueueModel.class);

        // the handlers itself
        this.queueHandler = new QueueHandler(queueProvider);
        this.playerModelHandler = new PlayerModelHandler(playerProvider, null);

        // setup the local server data
        new ServerConfiguration(new JsonConfigurationFile(new File("servers.json")));

        // setup the local server subscriber
        ServerConfiguration.LOCAL_SERVER.createModel().setupSubscriber(queueProvider, playerModelHandler);
    }
}