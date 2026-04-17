package com.qa.assertions;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;

public final class ApiAssertions {

    private ApiAssertions() {
    }

    public static ValidatableResponse validarStatus(Response response, int statusCode) {
        return response.then().statusCode(statusCode);
    }

    public static ValidatableResponse validarStatus200(Response response) {
        return validarStatus(response, 200);
    }

    public static ValidatableResponse validarStatus201(Response response) {
        return validarStatus(response, 201);
    }

    public static ValidatableResponse validarStatus400(Response response) {
        return validarStatus(response, 400);
    }

    public static ValidatableResponse validarStatus401(Response response) {
        return validarStatus(response, 401);
    }

    public static ValidatableResponse validarTempoRespostaMaxima(Response response, long tempoMillis) {
        return response.then().time(lessThan(tempoMillis));
    }

    public static ValidatableResponse validarErroCampo(Response response, String campo, String mensagem) {
        return validarStatus(response, 400)
                .body(campo, containsString(mensagem));
    }
}