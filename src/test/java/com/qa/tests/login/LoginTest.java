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
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Epic("API Automation")
@Feature("Autenticação")
@Tag("login")
@DisplayName("Login de usuário")
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
    @Story("Fluxo de login com credenciais válidas")
    @DisplayName("Deve realizar login com sucesso")
    public void deveFazerLoginComSucesso() {
        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest(usuario.getEmail(), usuario.getPassword());
        UsuarioLoginResponse loginResponse = usuarioService.login(loginRequest);

        assertNotNull(loginResponse);
        assertNotNull(loginResponse.getAuthorization());
    }
}
