package io.github.nosequel.storage.redis;

import io.github.nosequel.storage.StorageHandler;
import io.github.nosequel.storage.settings.settings.RedisSettings;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

@Getter
public class RedisStorageHandler extends StorageHandler {

    private final JedisPool pool;
    private final RedisSettings authSettings;

    public RedisStorageHandler(RedisSettings settings) {
        this.authSettings = settings;
        this.pool = settings.createObject();
    }

    public Jedis getResource() {
        return this.authSettings.auth(this.pool.getResource());
    }

    /**
     * Subscribe to a channel within the storage handler.
     *
     * @param channel      the channel to subscribe to
     * @param subscription the subscription action to accept
     */
    @Override
    public void subscribe(String channel, Consumer<String> subscription) {
        ForkJoinPool.commonPool().execute(() -> this.getResource().subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                subscription.accept(message);
            }
        }, channel));
    }

    /**
     * Publish a message to the storage provider
     *
     * @param channel the channel to publish the message to
     * @param message the message to publish
     */
    @Override
    public <T> void publish(String channel, T message) {
        ForkJoinPool.commonPool().execute(() -> this.getResource().publish(channel, message.toString()));
    }
}
