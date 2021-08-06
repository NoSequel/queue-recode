package io.github.nosequel.storage.redis.settings.impl;

import redis.clients.jedis.Jedis;

public class UserPasswordRedisSettings extends PasswordRedisSettings {

    private final String username;

    public UserPasswordRedisSettings(String hostname, int port, String username, String password) {
        super(hostname, port, password);
        this.username = username;
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
        jedis.auth(this.username, this.password);
        return jedis;
    }
}