package io.github.nosequel.storage.redis.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Getter
@RequiredArgsConstructor
public abstract class RedisSettings {

    protected final String hostname;
    protected final int port;

    /**
     * Create a new {@link JedisPool} object.
     * <p>
     * This object will be generated from the fields
     * within the current {@link RedisSettings} instance
     * </p>
     *
     * @return the newly created jedis pool
     */
    public abstract JedisPool createPool();

    /**
     * Authenticate the provided {@link Jedis} object
     * using the current authorization data.
     *
     * @param jedis the jedis object to authenticate for
     * @return the jedis object
     */
    public abstract Jedis authenticate(Jedis jedis);

}