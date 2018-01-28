package com.terry.spring.web.manager.cache.redis;

import com.terry.spring.web.manager.cache.CacheService;
import com.terry.spring.web.util.JsonUtil;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Created by TerryShisan on 2018/1/28.
 */
public class RedisCacheService implements CacheService{
    private final static RedisManager redisCacheManager = RedisManager.create();
    private static org.slf4j.Logger logger = LoggerFactory.getLogger("default");

    private void checkPut(Jedis jedis, String key, String value) {
        for (int i = 0; i < 100; i++) {
            logger.info("repet set redis key is: " + key);
            String result = jedis.get(key);
            if (result!=null && result.equalsIgnoreCase(value)){
                break;
            } else {
                jedis.set(key, value);
            }
        }

    }


    @Override
    public boolean put(String key, String value) {
        return put(key, value, redisCacheManager.getJedisConfig().getTtlSeconds());
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = redisCacheManager.getResource();
            key = redisCacheManager.getJedisConfig().getKeyPrefix() + "|" + key;
            logger.info("get from redis, key is: " + key);
            result = jedis.get(key);
            logger.info("End get from redis, key is: " + key + " value is: " + result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public boolean delete(String key) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = redisCacheManager.getResource();
            key = redisCacheManager.getJedisConfig().getKeyPrefix() + "|" + key;
            result = jedis.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result > 0 ? true : false;
    }

    @Override
    public boolean putMap(String key, Map<String, Object> resultMap) {
        //return false;
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = redisCacheManager.getResource();
            key = redisCacheManager.getJedisConfig().getKeyPrefix() + "|" + key;
            jedis.set(key, JsonUtil.toJson(resultMap));
            checkPut(jedis, key, JsonUtil.toJson(resultMap));
            jedis.expire(key, redisCacheManager.getJedisConfig().getTtlSeconds());
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getMap(String key) {
        //return null;
        Jedis jedis = null;
        String resultJson = null;
        try {
            jedis = redisCacheManager.getResource();
            key = redisCacheManager.getJedisConfig().getKeyPrefix() + "|" + key;
            resultJson = jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        try{
            return JsonUtil.toObject(resultJson, Map.class);
        }catch (RuntimeException e){
            return null;
        }

//		return gson.fromJson(resultJson, new TypeToken<Map<String, Object>>(){}.getType());*/
    }

    // ncr不支持keys命令
    @Override
    public boolean clear(String connectionString) {
        return delete(connectionString);
    }

    // TODO ncr不支持flushAll命令
    @Override
    public boolean clearAll() {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = redisCacheManager.getResource();
            jedis.flushAll();
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public boolean putPermanent(String key, String value) {
        return put(key, value, 0);
    }

    @Override
    public boolean putListElement(String key, String value) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = redisCacheManager.getResource();
            key = redisCacheManager.getJedisConfig().getKeyPrefix() + "|" + key;
            long resultLong = jedis.rpush(key, value);
            if (resultLong == 1) {
                // TODO 这里的时间待定, 可能要新加一个配置项
                jedis.expire(key, redisCacheManager.getJedisConfig().getTtlSeconds());
            }
            result = resultLong > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public List<String> getList(String key) {
        Jedis jedis = null;
        List<String> result = null;
        try {
            jedis = redisCacheManager.getResource();
            key = redisCacheManager.getJedisConfig().getKeyPrefix() + "|" + key;
            result = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    // ttlSeconds为0表示永久有效
    private boolean put(String key, String value, int ttlSeconds) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = redisCacheManager.getResource();
            key = redisCacheManager.getJedisConfig().getKeyPrefix() + "|" + key;
            logger.info("put redis key is: " + key + " ,value is " + value);
            jedis.set(key, value);
            checkPut(jedis, key, value);
            if (ttlSeconds > 0) {
                jedis.expire(key, ttlSeconds);
            }
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public boolean deleteListElement(String key, String value) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = redisCacheManager.getResource();
            key = redisCacheManager.getJedisConfig().getKeyPrefix() + "|" + key;
            long resultLong = jedis.lrem(key, 0, value);
            result = resultLong > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

}
