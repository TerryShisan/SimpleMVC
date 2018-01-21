package com.terry.spring.web.config.loader;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by TerryShisan on 2017/4/30.
 */
public class XmlLoader extends Loader{

    public void load(String path) throws Exception {
        PropertyFromXml newProperty = new PropertyFromXml();
        XMLConfiguration xmlConfiguration = new XMLConfiguration(path);
        List controllers = xmlConfiguration.configurationsAt("controllers.controller");
        if (controllers == null || controllers.isEmpty()) {
            throw new ConfigurationException("controllers/controller is not found!");
        }
        Iterator i$ = controllers.iterator();
        while (i$.hasNext()) {
            HierarchicalConfiguration item = (HierarchicalConfiguration) i$.next();
            String clazz = (String) item.getProperty("class");
            String urlPath = (String) item.getProperty("path");
            if (StringUtils.isEmpty(clazz) || StringUtils.isEmpty(urlPath)) {
                continue;
            }
            newProperty.controllers.put(urlPath, clazz);
        }
        property = newProperty;
    }
}
