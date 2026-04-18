package com.qa.service;

import com.qa.assertions.ApiAssertions;
import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioLoginResponse;
import com.qa.factory.UsuarioFactory;
import io.restassured.response.Response;

public class UsuarioService {

    private final UsuarioClient usuarioClient;

    public UsuarioService(UsuarioClient usuarioClient) {
        this.usuarioClient = usuarioClient;
    }

    public Response criarUsuario(Usuario usuario) {
        return usuarioClient.criarUsuario(usuario);
    }

    public UsuarioLoginResponse login(UsuarioLoginRequest loginRequest) {
        return usuarioClient.login(loginRequest);
    }

    public String obterToken(UsuarioLoginRequest loginRequest) {
        return login(loginRequest).getAuthorization();
    }

    public Response listarUsuarios() {
        return usuarioClient.listarUsuarios();
    }

    public Response deletarUsuario(String id) {
        return usuarioClient.deletarUsuario(id);
    }

    public Response deletarUsuarioComToken(String id, String token) {
        return usuarioClient.deletarUsuarioComToken(id, token);
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