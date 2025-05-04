package com.newsletter;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static Properties load() {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            props.load(input);
            return props;
        } catch (Exception e) {
            throw new RuntimeException("error loading config.properties", e);
        }
    }
}
