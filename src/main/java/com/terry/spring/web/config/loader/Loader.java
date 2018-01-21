package com.terry.spring.web.config.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by TerryShisan on 2017/4/30.
 */
public abstract class Loader {

    protected volatile PropertyFromXml property;
    protected volatile Properties properties = new Properties();

    public Map<String,String> getControllers() {
        return property.controllers;
    }

    public Map<String,String> getURLs() {
        return property.urls;
    }

    public Properties getProperties(){
        return properties;
    }

    public static Loader load()throws Exception{
        XmlLoader loader1 = new XmlLoader();
        loader1.load("controllers.xml");
        Properties properties = loader1.getProperties();
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.load("server.properties");
        properties.putAll(propertiesLoader.getProperties());
        return loader1;
    }

    class PropertyFromXml {
        public Map<String, String>  controllers = new HashMap<>();
        public Map<String, String>  urls = new HashMap<>();
    }

}
