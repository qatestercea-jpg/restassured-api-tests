package com.qa.assertions;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.containsString;

public final class ApiAssertions {

    private ApiAssertions() {
    }

    public static ValidatableResponse validarStatus200(Response response) {
        return response.then().statusCode(200);
    }

    public static ValidatableResponse validarStatus201(Response response) {
        return response.then().statusCode(201);
    }

    public static ValidatableResponse validarErroCampo(Response response, String campo, String mensagem) {
        return response.then()
                .statusCode(400)
                .body(campo, containsString(mensagem));
    }
}