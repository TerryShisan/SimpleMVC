package com.terry.spring.web.manager.cache.redis;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by TerryShisan on 2018/1/28.
 */
@Data
public class JedisConfig {

    private JedisPoolConfig poolConfig;
    private String host;
    private int port;
    private int connectionTimeout;
    private int soTimeout;
    private String password;
    private int database;
    private String clientName;
    // the special of model prefix key
    private String keyPrefix;
    private int ttlSeconds;

    public JedisConfig() {
        // 目前JedisPoolConfig不读配置，提供默认值
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxWaitMillis(3000);
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(0);

        poolConfig.setTestWhileIdle(true);
        poolConfig.setMaxWaitMillis(1000 * 100);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setTimeBetweenEvictionRunsMillis(30 * 60000);
        poolConfig.setMinEvictableIdleTimeMillis(30 * 60000);
        this.setPoolConfig(poolConfig);
    }
}
