package com.terry.spring.web.manager.cache.redis;

/**
 * Created by TerryShisan on 2018/4/1.
 */
public class Redis {

    private RedisCacheService redis = null;

    private Redis() {

    }

    public static Redis getInstance() {
        return RedisSingleton.INSTANCE.getInstance();
    }

    private enum RedisSingleton {
        INSTANCE;

        private Redis singleton;

        RedisSingleton() {
            singleton = new Redis();
        }

        public Redis getInstance() {
            return singleton;
        }
    }

}
