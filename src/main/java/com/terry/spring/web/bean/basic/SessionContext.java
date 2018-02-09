package com.terry.spring.web.bean.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TerryShisan on 2018/2/9.
 * session must be thread safity
 */
public class SessionContext {
    private static final ThreadLocal<Map<String,String>> context = new ThreadLocal<Map<String,String>>(){
        protected Map<String,String> initialValue() {
            return new HashMap<>();
        }
    };

    public static void set(String key,String value) {
        context.get().put(key,value);
    }

    public static String getKey(){
        return String.valueOf(context.get().get("key"));
    }

    public static void clear() {
        context.get().clear();
    }
}
