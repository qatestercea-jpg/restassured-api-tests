package com.qa.base;

import com.qa.config.EnvironmentConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification REQUEST_SPEC;

    @BeforeAll
    public static void setup() {
        REQUEST_SPEC = new RequestSpecBuilder()
                .setBaseUri(EnvironmentConfig.getBaseUri())
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.requestSpecification = REQUEST_SPEC;
        RestAssured.filters(new AllureRestAssured(), new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}