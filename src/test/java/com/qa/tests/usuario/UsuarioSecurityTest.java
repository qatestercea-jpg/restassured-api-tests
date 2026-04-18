package com.qa.tests.usuario;

import com.qa.assertions.UsuarioAssertions;
import com.qa.dto.Usuario;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Epic("API Automation")
@Feature("Segurança")
@Tag("security")
@Tag("usuario")
@Tag("regression")
@DisplayName("Segurança de usuário")
public class UsuarioSecurityTest extends UsuarioTestBase {

    @Test
    @Story("Validação de autorização de exclusão de usuário")
    @DisplayName("Endpoint aceita exclusão de usuário sem autenticação")
    public void devePermitirDeletarUsuarioSemAutenticacao() {
        // Observação: o endpoint DELETE /usuarios/{id} devolve 200 mesmo sem
        // credenciais.
        // Isso representa uma possível vulnerabilidade de autorização do serviço.
        String userId = usuarioService.criarUsuarioValidoERetornarId();

        Response deleteResponse = usuarioService.deletarUsuarioComValidacao(userId);
        UsuarioAssertions.validarUsuarioDeletado(deleteResponse);
    }

    @Test
    @Story("Validação de token inválido na exclusão")
    @DisplayName("Endpoint aceita exclusão com token inválido")
    public void devePermitirDeletarUsuarioComTokenInvalido() {
        // Observação: o endpoint aceita um token inválido e ainda assim realiza a
        // deleção.
        Usuario usuario = criarUsuarioValido();
        String userId = criarUsuarioERetornarId(usuario);
        String invalidToken = "token-invalido-" + System.currentTimeMillis();

        UsuarioAssertions.validarUsuarioDeletado(usuarioService.deletarUsuarioComToken(userId, invalidToken));
    }
}
