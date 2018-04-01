package com.terry.spring.web.manager.cache;

import java.util.List;
import java.util.Map;

/**
 * Created by TerryShisan on 2018/1/28.
 */
public interface CacheService {

    public boolean put(String key, String value);

    public boolean put(String key, String value, int ttlSeconds);

    public boolean putPermanent(String key, String value);

    public String get(String key);

    public boolean putListElement(String key, String value);

    public List<String> getList(String key);

    public boolean putMap(String key, Map<String, Object> map);

    public Map<String, Object> getMap(String key);

    public boolean deleteListElement(String key, String value);

    public boolean delete(String key);

    public boolean clear(String connectionString);

    public boolean clearAll();
}
