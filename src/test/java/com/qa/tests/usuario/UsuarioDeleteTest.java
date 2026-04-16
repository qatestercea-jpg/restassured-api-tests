package com.qa.tests.usuario;

import com.qa.assertions.ApiAssertions;
import com.qa.dto.Usuario;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

@Epic("API Automation")
@Feature("Usuário")
@Tag("usuario")
@Tag("regression")
@DisplayName("Exclusão de usuário")
public class UsuarioDeleteTest extends UsuarioTestBase {

    @Test
    @Story("Exclusão de usuário cadastrado")
    @DisplayName("Deve excluir usuário criado com sucesso")
    public void deveExcluirUsuarioCriadoComSucesso() {
        Usuario usuario = criarUsuarioValido();
        Response createResponse = criarUsuarioComSucesso(usuario);
        String userId = createResponse.jsonPath().getString("_id");

        ApiAssertions.validarStatus200(usuarioService.listarUsuarios())
                .body("usuarios.email", hasItem(usuario.getEmail()));

        usuarioService.deletarUsuario(userId)
                .then()
                .statusCode(200);

        ApiAssertions.validarStatus200(usuarioService.listarUsuarios())
                .body("usuarios.email", not(hasItem(usuario.getEmail())));
    }
}
