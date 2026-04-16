package com.qa.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static RequestSpecification REQUEST_SPEC;

    @BeforeAll
    public static void setup() {
        REQUEST_SPEC = new RequestSpecBuilder()
                .setBaseUri("https://serverest.dev")
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.requestSpecification = REQUEST_SPEC;
    }
}