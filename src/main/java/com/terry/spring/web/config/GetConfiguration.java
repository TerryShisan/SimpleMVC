package com.terry.spring.web.config;

import com.terry.spring.web.config.loader.Loader;

/**
 * Created by TerryShisan on 2017/4/30.
 */
public class GetConfiguration {
    protected static Loader loader;

    public static void load() throws Exception {
        loader = Loader.load();
    }
}
