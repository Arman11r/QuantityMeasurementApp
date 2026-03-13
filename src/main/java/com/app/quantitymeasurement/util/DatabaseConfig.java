package com.app.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();

    static {
        try {

            InputStream input =
                    DatabaseConfig.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}