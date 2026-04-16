package com.qa.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class EnvironmentConfig {

    private static final String SYSTEM_ENV_KEY = "env";
    private static final String ENV_VAR_KEY = "ENV";
    private static final String PROPERTIES_FILE = "environment.properties";

    private static final Properties PROPERTIES = loadProperties();
    private static final String DEFAULT_ENV = PROPERTIES.getProperty("default.env", "qa");
    private static final String ENVIRONMENT = determineEnvironment();

    private EnvironmentConfig() {
    }

    private static String determineEnvironment() {
        String systemProperty = System.getProperty(SYSTEM_ENV_KEY);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty.trim();
        }

        String envVariable = System.getenv(ENV_VAR_KEY);
        return envVariable != null && !envVariable.isBlank() ? envVariable.trim() : DEFAULT_ENV;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream stream = EnvironmentConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (stream == null) {
                throw new IllegalStateException("environment.properties not found in classpath");
            }
            properties.load(stream);
        } catch (IOException exception) {
            throw new RuntimeException("Falha ao carregar environment.properties", exception);
        }
        return properties;
    }

    public static String getBaseUri() {
        return PROPERTIES.getProperty(ENVIRONMENT + ".baseUri", PROPERTIES.getProperty(DEFAULT_ENV + ".baseUri"));
    }

    public static String getEnvironment() {
        return ENVIRONMENT;
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(ENVIRONMENT + "." + key, PROPERTIES.getProperty(key));
    }
}
