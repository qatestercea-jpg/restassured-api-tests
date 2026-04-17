package com.qa.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {

    protected static RequestSpecification REQUEST_SPEC;

    private static final String PROPERTIES_FILE = "environment.properties";
    private static final String SYSTEM_ENV_KEY = "env";
    private static final String ENV_VAR_KEY = "ENV";
    private static final Properties PROPERTIES = loadProperties();
    private static final String DEFAULT_ENV = PROPERTIES.getProperty("default.env", "qa");
    private static final String ENVIRONMENT = determineEnvironment();

    @BeforeAll
    public static void setup() {
        REQUEST_SPEC = new RequestSpecBuilder()
                .setBaseUri(getBaseUri())
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.requestSpecification = REQUEST_SPEC;
        RestAssured.filters(new AllureRestAssured(), new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private static String getBaseUri() {
        return PROPERTIES.getProperty(ENVIRONMENT + ".baseUri", PROPERTIES.getProperty(DEFAULT_ENV + ".baseUri"));
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream stream = BaseTest.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (stream == null) {
                throw new IllegalStateException(PROPERTIES_FILE + " not found in classpath");
            }
            properties.load(stream);
        } catch (IOException exception) {
            throw new RuntimeException("Falha ao carregar " + PROPERTIES_FILE, exception);
        }
        return properties;
    }

    private static String determineEnvironment() {
        String systemProperty = System.getProperty(SYSTEM_ENV_KEY);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty.trim();
        }

        String envVariable = System.getenv(ENV_VAR_KEY);
        return envVariable != null && !envVariable.isBlank() ? envVariable.trim() : DEFAULT_ENV;
    }
}
