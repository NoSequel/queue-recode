package io.github.nosequel.storage.redis.settings.impl;

import io.github.nosequel.storage.redis.settings.RedisSettings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class PasswordRedisSettings extends RedisSettings {

    protected final String password;

    public PasswordRedisSettings(String hostname, int port, String password) {
        super(hostname, port);
        this.password = password;
    }

    /**
     * Create a new {@link JedisPool} object.
     * <p>
     * This object will be generated from the fields
     * within the current {@link RedisSettings} instance
     * </p>
     *
     * @return the newly created jedis pool
     */
    @Override
    public JedisPool createPool() {
        return new JedisPool(
                this.hostname,
                this.port
        );
    }

    /**
     * Authenticate the provided {@link Jedis} object
     * using the current authorization data.
     *
     * @param jedis the jedis object to authenticate for
     * @return the jedis object
     */
    @Override
    public Jedis authenticate(Jedis jedis) {
        jedis.auth(this.password);
        return jedis;
    }
}
