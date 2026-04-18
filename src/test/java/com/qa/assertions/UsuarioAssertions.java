package com.qa.assertions;

import com.qa.dto.UsuarioLoginResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public final class UsuarioAssertions {

    private UsuarioAssertions() {
    }

    public static ValidatableResponse validarUsuarioCriado(Response response) {
        return ApiAssertions.validarStatus201(response);
    }

    public static ValidatableResponse validarListagemUsuarios(Response response) {
        return ApiAssertions.validarStatus200(response);
    }

    public static ValidatableResponse validarListagemUsuarios(Response response, String email) {
        return ApiAssertions.validarStatus200(response)
                .body("usuarios.email", hasItem(email));
    }

    public static ValidatableResponse validarErroDeCriacao(Response response) {
        return ApiAssertions.validarStatus400(response);
    }

    public static ValidatableResponse validarUsuarioDeletado(Response response) {
        return ApiAssertions.validarStatus200(response);
    }

    public static ValidatableResponse validarEsquemaUsuario(Response response) {
        return ApiAssertions.validarEsquema(response, "schema/create-user-schema.json");
    }

    public static ValidatableResponse validarUsuarioCriadoComSucesso(Response response) {
        return validarUsuarioCriado(response);
    }

    public static ValidatableResponse validarListagemContemEmail(Response response, String email) {
        return validarListagemUsuarios(response, email);
    }

    public static ValidatableResponse validarListagemNaoContemEmail(Response response, String email) {
        return ApiAssertions.validarStatus200(response)
                .body("usuarios.email", not(hasItem(email)));
    }

    public static ValidatableResponse validarRequisicaoComSucesso(Response response) {
        return ApiAssertions.validarStatus200(response);
    }

    public static ValidatableResponse validarDelecaoComSucesso(Response response) {
        return validarUsuarioDeletado(response);
    }

    public static ValidatableResponse validarStatus400(Response response) {
        return ApiAssertions.validarStatus400(response);
    }

    public static ValidatableResponse validarErroCampo(Response response, String campo, String mensagem) {
        return ApiAssertions.validarErroCampo(response, campo, mensagem);
    }

    public static void validarTokenNaoNulo(UsuarioLoginResponse loginResponse) {
        assertThat(loginResponse, notNullValue());
        assertThat(loginResponse.getAuthorization(), not(isEmptyString()));
    }
}
