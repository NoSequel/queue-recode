package io.github.nosequel.queue.shared;

import io.github.nosequel.command.CommandHandler;
import io.github.nosequel.config.json.JsonConfigurationFile;
import io.github.nosequel.queue.shared.command.JoinQueueCommand;
import io.github.nosequel.queue.shared.command.adapter.QueueModelTypeAdapter;
import io.github.nosequel.queue.shared.config.MessageConfiguration;
import io.github.nosequel.queue.shared.config.ServerConfiguration;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerModelHandler;
import io.github.nosequel.queue.shared.model.queue.QueueHandler;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import io.github.nosequel.queue.shared.model.queue.serialize.QueueModelSerializer;
import io.github.nosequel.queue.shared.model.server.ServerHandler;
import io.github.nosequel.queue.shared.model.server.ServerModel;
import io.github.nosequel.storage.redis.RedisStorageHandler;
import io.github.nosequel.storage.settings.settings.impl.NoAuthRedisSettings;
import io.github.nosequel.storage.storage.impl.RedisStorageProvider;
import lombok.Getter;

import java.io.File;

@Getter
public abstract class QueueBootstrap {

    private final File dataFolder;

    public QueueBootstrap(File dataFolder) {
        this.dataFolder = dataFolder;

        if (!this.dataFolder.exists()) {
            if (this.dataFolder.mkdirs()) {
                System.out.println("Made directory \"" + dataFolder.getPath() + "\"");
            }
        }
    }

    protected QueueHandler queueHandler;
    protected ServerHandler serverHandler;
    protected PlayerModelHandler playerModelHandler;

    public void load() {
        // redis stuff
        final RedisStorageHandler storageHandler = new RedisStorageHandler(new NoAuthRedisSettings("panel.clox.us", 6379));
        final QueueModelSerializer serializer = new QueueModelSerializer();

        storageHandler.getSerializerMap().put(QueueModel.class, serializer);

        final RedisStorageProvider<PlayerModel> playerProvider = new RedisStorageProvider<>("players", storageHandler, PlayerModel.class);
        final RedisStorageProvider<QueueModel> queueProvider = new RedisStorageProvider<>("queues", storageHandler, QueueModel.class);
        final RedisStorageProvider<ServerModel> serverProvider = new RedisStorageProvider<>("servers", storageHandler, ServerModel.class);

        serializer.setStorageProvider(queueProvider);

        // the handlers itself
        this.queueHandler = new QueueHandler(queueProvider);
        this.serverHandler = new ServerHandler(serverProvider);
        this.playerModelHandler = new PlayerModelHandler(playerProvider);

        serializer.setServerHandler(serverHandler);

        // setup the local server data
        new ServerConfiguration(new JsonConfigurationFile(new File(this.dataFolder, "servers.json")));
        new MessageConfiguration(new JsonConfigurationFile(new File(this.dataFolder, "messages.json")));
    }

    public void registerCommands(CommandHandler commandHandler) {
        commandHandler.registerTypeAdapter(QueueModel.class, new QueueModelTypeAdapter(this.queueHandler));

        commandHandler.registerCommand(new JoinQueueCommand(this.playerModelHandler, this.queueHandler));
    }

}