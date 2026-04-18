package com.qa.service;

import com.qa.assertions.ApiAssertions;
import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioLoginResponse;
import com.qa.factory.UsuarioFactory;
import com.qa.utils.RetryUtils;
import io.restassured.response.Response;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class UsuarioService {

    private final UsuarioClient usuarioClient;

    public UsuarioService(UsuarioClient usuarioClient) {
        this.usuarioClient = usuarioClient;
    }

    public Response criarUsuario(Usuario usuario) {
        return RetryUtils.executeWithRetry(
                () -> usuarioClient.criarUsuario(usuario),
                response -> response == null || response.getStatusCode() >= 500,
                "criarUsuario");
    }

    public UsuarioLoginResponse login(UsuarioLoginRequest loginRequest) {
        return RetryUtils.executeWithRetry(
                () -> usuarioClient.login(loginRequest),
                response -> false,
                "login");
    }

    public String obterToken(UsuarioLoginRequest loginRequest) {
        return login(loginRequest).getAuthorization();
    }

    public Response listarUsuarios() {
        Response response = RetryUtils.executeWithRetry(
                () -> usuarioClient.listarUsuarios(),
                retryResponse -> retryResponse == null || retryResponse.getStatusCode() >= 500,
                "listarUsuarios");
        ApiAssertions.validarStatus200(response);
        return response;
    }

    public Response deletarUsuario(String id) {
        return RetryUtils.executeWithRetry(
                () -> usuarioClient.deletarUsuario(id),
                response -> response == null || response.getStatusCode() >= 500,
                "deletarUsuario");
    }

    public Response deletarUsuarioComToken(String id, String token) {
        return RetryUtils.executeWithRetry(
                () -> usuarioClient.deletarUsuarioComToken(id, token),
                response -> response == null || response.getStatusCode() >= 500,
                "deletarUsuarioComToken");
    }

    public String criarUsuarioERetornarId(Usuario usuario) {
        Response response = criarUsuario(usuario);

        if (response == null) {
            throw new IllegalStateException("Resposta de criação de usuário é nula");
        }

        String responseBody = response.getBody().asString();
        System.out.println("DEBUG criarUsuarioERetornarId response body: " + responseBody);

        ApiAssertions.validarStatus201(response);

        String id = response.jsonPath().getString("_id");
        if (id == null || id.isBlank()) {
            id = response.jsonPath().getString("id");
        }

        if (id == null || id.isBlank()) {
            throw new IllegalStateException("Falha ao extrair ID do usuário criado. Resposta: " + responseBody);
        }

        return id;
    }

    public String criarUsuarioValidoERetornarId() {
        Usuario usuario = UsuarioFactory.usuarioValido();
        return criarUsuarioERetornarId(usuario);
    }

    public Response criarUsuarioInvalido() {
        Usuario usuario = UsuarioFactory.usuarioSemEmail();
        Response response = criarUsuario(usuario);
        ApiAssertions.validarStatus400(response);
        return response;
    }

    public Response deletarUsuarioComValidacao(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID de usuário não pode ser nulo ou vazio");
        }
        Response response = deletarUsuario(id);
        ApiAssertions.validarStatus200(response);
        return response;
    }
}