package com.terry.spring.web.config.loader;

import com.terry.spring.web.config.GetConfiguration;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by TerryShisan on 2017/4/30.
 */
public class PropertiesLoader extends Loader{
    public void load(String path) throws Exception {
        InputStream inputStream = GetConfiguration.class.getClassLoader().getResourceAsStream("server.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        this.properties = properties;
    }
}
