package com.qa.tests.login;

import com.qa.base.BaseTest;
import com.qa.builder.UsuarioBuilder;
import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioLoginResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginTest extends BaseTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void deveFazerLoginComSucesso() {
        Usuario usuario = UsuarioBuilder.usuarioValido().build();
        usuarioClient.criarUsuario(usuario).then().statusCode(201);

        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest(usuario.getEmail(), usuario.getPassword());
        UsuarioLoginResponse loginResponse = usuarioClient.login(loginRequest);

        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getAuthorization());
    }
}
