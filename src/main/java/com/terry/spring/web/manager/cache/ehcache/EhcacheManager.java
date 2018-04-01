package com.terry.spring.web.manager.cache.ehcache;


import com.terry.spring.web.manager.cache.CacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.List;
import java.util.Map;

/**
 * Created by TerryShisan on 2018/1/28.
 */
public class EhcacheManager implements CacheService{
    private final static CacheManager cacheManager = CacheManager.create();
    private final static Cache queryIdCache = cacheManager.getCache("queryIdCache");

    @Override
    public boolean put(String key, String value) {
        Element element = new Element(key, value);
        queryIdCache.put(element);
        return true;
    }

    @Override
    public String get(String key) {
        Element element = queryIdCache.get(key);
        if (element != null) {
            return (String) element.getObjectValue();
        }
        return null;
    }

    @Override
    public boolean delete(String key) {
        return queryIdCache.remove(key);
    }

    @Override
    public boolean putMap(String key, Map<String, Object> resultMap) {
//		String[] strArray = key.split("\\|");
//		if (strArray.length == 2) {
//			Cache cache = cacheManager.getCache(strArray[0]);
//			if (cache == null) {
//				cacheManager.addCache(strArray[0]);
//			}
//			cache = cacheManager.getCache(strArray[0]);
//			if (cache != null) {
        Element element = new Element(key, resultMap);
        queryIdCache.put(element);
        return true;
//			}
//		}
//		return false;
    }

    @Override
    public Map<String, Object> getMap(String key) {
//		String[] strArray = key.split("\\|");
//		if (strArray.length == 2) {
//			Cache cache = cacheManager.getCache(strArray[0]);
//			if (cache != null) {
        Element element = queryIdCache.get(key);
        if (element != null) {
            return (Map<String, Object>) element.getObjectValue();
        }
        return null;
//			}
//		}
//		return null;
    }

    @Override
    public boolean putPermanent(String key, String value) {
        // TODO finish
        return put(key, value);
    }

    @Override
    public boolean putListElement(String key, String value) {
        // TODO finish
        return false;
    }

    @Override
    public List<String> getList(String key) {
        // TODO finish
        return null;
    }

    @Override
    public boolean deleteListElement(String key, String value) {
        // TODO 暂未实现
        return false;
    }

    @Override
    public boolean clear(String connectionString) {
        Cache cache = cacheManager.getCache(connectionString);
        if (cache == null) return true;
        cacheManager.removeCache(connectionString);
        return true;
    }

    @Override
    public boolean clearAll() {
        cacheManager.removalAll();
        return true;
    }

    @Override
    public boolean put(String key, String value, int ttlSeconds) {
        return false;
    }
}
