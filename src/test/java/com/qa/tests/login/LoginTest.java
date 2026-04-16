package com.qa.tests.login;

import com.qa.assertions.ApiAssertions;
import com.qa.base.BaseTest;
import com.qa.builder.UsuarioBuilder;
import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioLoginResponse;
import com.qa.factory.UsuarioFactory;
import com.qa.service.UsuarioService;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginTest extends BaseTest {

    private final UsuarioService usuarioService = new UsuarioService(new UsuarioClient());

    private Usuario criarUsuarioValido() {
        return UsuarioFactory.usuarioValido();
    }

    private Response criarUsuarioComSucesso(Usuario usuario) {
        Response response = usuarioService.criarUsuario(usuario);
        ApiAssertions.validarStatus201(response);
        return response;
    }

    @Test
    public void deveFazerLoginComSucesso() {
        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest(usuario.getEmail(), usuario.getPassword());
        UsuarioLoginResponse loginResponse = usuarioService.login(loginRequest);

        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getAuthorization());
    }
}
