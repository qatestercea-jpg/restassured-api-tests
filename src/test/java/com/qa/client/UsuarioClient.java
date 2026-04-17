package com.qa.client;

import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioLoginResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UsuarioClient {

    private static final String USUARIOS = "/usuarios";
    private static final String LOGIN = "/login";

    // 🔥 método base (evita repetição)
    private RequestSpecification request() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public Response criarUsuario(Usuario usuario) {
        return request()
                .body(usuario)
                .post(USUARIOS);
    }

    public UsuarioLoginResponse login(UsuarioLoginRequest loginRequest) {
        return request()
                .body(loginRequest)
                .post(LOGIN)
                .as(UsuarioLoginResponse.class);
    }

    public Response listarUsuarios() {
        return request()
                .get(USUARIOS);
    }

    public Response deletarUsuario(String id) {
        return request()
                .delete(USUARIOS + "/{id}", id);
    }

    private RequestSpecification requestWithAuth(String token) {
        return request().header("Authorization", token);
    }

    public Response deletarUsuarioComToken(String id, String token) {
        return requestWithAuth(token)
                .delete(USUARIOS + "/{id}", id);
    }
}