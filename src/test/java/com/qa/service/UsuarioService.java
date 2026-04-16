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
}