package com.qa.client;

import com.qa.constants.Endpoints;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioLoginResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UsuarioClient {

    // 🔥 método base (evita repetição)
    private RequestSpecification request() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public Response criarUsuario(Usuario usuario) {
        return request()
                .body(usuario)
                .post(Endpoints.USUARIOS);
    }

    public UsuarioLoginResponse login(UsuarioLoginRequest loginRequest) {
        return request()
                .body(loginRequest)
                .post(Endpoints.LOGIN)
                .as(UsuarioLoginResponse.class);
    }

    public Response listarUsuarios() {
        return request()
                .get(Endpoints.USUARIOS);
    }

    public Response deletarUsuario(String id) {
        return request()
                .delete(Endpoints.USUARIOS + "/{id}", id);
    }
}