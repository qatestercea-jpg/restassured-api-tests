package com.qa.service;

import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import io.restassured.response.Response;

public class UsuarioService {

    private static final UsuarioClient usuarioClient = new UsuarioClient();

    public static Response criarUsuario(Usuario usuario) {
        return usuarioClient.criarUsuario(usuario);
    }

    public static String loginRetornandoToken(UsuarioLoginRequest loginRequest) {
        return usuarioClient.login(loginRequest).getAuthorization();
    }

    public static Response listarUsuarios() {
        return usuarioClient.listarUsuarios();
    }

    public static Response listarUsuariosComToken(String authorizationToken) {
        return usuarioClient.listarUsuariosComToken(authorizationToken);
    }

    public static Response deletarUsuario(String id) {
        return usuarioClient.deletarUsuario(id);
    }

    public static Response deletarUsuarioComToken(String id, String authorizationToken) {
        return usuarioClient.deletarUsuarioComToken(id, authorizationToken);
    }
}