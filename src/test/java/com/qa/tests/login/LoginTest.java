package com.qa.tests.login;

import com.qa.assertions.UsuarioAssertions;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioLoginResponse;
import com.qa.tests.usuario.UsuarioTestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Epic("API Automation")
@Feature("Autenticação")
@Tag("login")
@DisplayName("Login de usuário")
public class LoginTest extends UsuarioTestBase {

    @Test
    @Story("Fluxo de login com credenciais válidas")
    @DisplayName("Deve realizar login com sucesso")
    public void deveFazerLoginComSucesso() {
        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest(usuario.getEmail(), usuario.getPassword());
        UsuarioLoginResponse loginResponse = usuarioService.login(loginRequest);

        UsuarioAssertions.validarTokenNaoNulo(loginResponse);
    }
}
