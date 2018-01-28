package com.terry.spring.web.manager.cache.redis;

import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

import java.io.IOException;

/**
 * Created by TerryShisan on 2018/1/28.
 */
@Data
@NoArgsConstructor
public class RedisManager {
    private static JedisPool pool;
    private static RedisManager singleton;
    private static JedisConfig jedisConfig;

    private static RedisManager newInstance() throws RuntimeException {
        try {
            jedisConfig = new JedisConfig(); //TODO: get Redis from properties
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        if (jedisConfig != null) {
            if (jedisConfig.getPassword() != null) {
                pool = new JedisPool(jedisConfig.getPoolConfig(),
                        jedisConfig.getHost(), jedisConfig.getPort(), Protocol.DEFAULT_TIMEOUT, jedisConfig.getPassword());
            }
            else {
                pool = new JedisPool(jedisConfig.getPoolConfig(), jedisConfig.getHost(), jedisConfig.getPort());
            }
            checkPool(pool);
            return new RedisManager();
        }
        throw new RuntimeException("can not create a RedisCacheManager instance");
    }

    private static void checkPool(JedisPool pool) throws RuntimeException {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
        }
        catch (Exception e) {
            throw new RuntimeException("can not connect to the redis server!", e);
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static RedisManager create() throws RuntimeException {
        if (singleton != null) {
            return singleton;
        }
        synchronized (RedisManager.class) {
            if (singleton == null) {
                singleton = newInstance();
            }
            return singleton;
        }
    }

    public Jedis getResource() {
        return getJedisPool().getResource();
    }

    private JedisPool getJedisPool() {
        return pool;
    }

    public JedisConfig getJedisConfig() {
        return jedisConfig;
    }

    public static void destory() {
        if (pool != null) {
            pool.destroy();
            pool = null;
        }
        if (singleton != null) {
            singleton = null;
        }
    }
}
