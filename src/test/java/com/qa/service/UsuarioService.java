package com.qa.service;

import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioLoginResponse;
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

        if (response.getStatusCode() != 201) {
            throw new IllegalStateException(
                    "Falha ao criar usuário: status esperado 201, recebido " + response.getStatusCode() + ". Resposta: "
                            + responseBody);
        }

        String id = response.jsonPath().getString("_id");
        if (id == null || id.isBlank()) {
            id = response.jsonPath().getString("id");
        }

        if (id == null || id.isBlank()) {
            throw new IllegalStateException("Falha ao extrair ID do usuário criado. Resposta: " + responseBody);
        }

        return id;
    }
}