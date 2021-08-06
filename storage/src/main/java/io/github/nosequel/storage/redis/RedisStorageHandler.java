package io.github.nosequel.storage.redis;

import io.github.nosequel.storage.StorageHandler;
import io.github.nosequel.storage.redis.settings.RedisSettings;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Getter
public class RedisStorageHandler extends StorageHandler {

    private final JedisPool pool;
    private final RedisSettings settings;

    public RedisStorageHandler(RedisSettings settings) {
        this.settings = settings;
        this.pool = settings.createPool();
    }

    public Jedis getResource() {
        return this.settings.authenticate(this.pool.getResource());
    }

}
