package com.qa.tests.usuario;

import com.qa.assertions.ApiAssertions;
import com.qa.assertions.UsuarioAssertions;
import com.qa.dto.Usuario;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasItem;

@Epic("API Automation")
@Feature("Usuário")
@Tag("usuario")
@Tag("performance")
@DisplayName("Listagem de usuários")
public class UsuarioListTest extends UsuarioTestBase {

    @Test
    @Story("Consulta de usuários cadastrados")
    @DisplayName("Deve listar usuário cadastrado")
    public void deveListarUsuarioCadastrado() {
        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        UsuarioAssertions.validarListagemContemEmail(usuarioService.listarUsuarios(), usuario.getEmail());
    }

    @Test
    @Story("Performance de listagem de usuários")
    @DisplayName("Deve retornar listagem dentro do tempo esperado")
    public void deveListarUsuariosDentroDoTempoMaximo() {
        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        Response response = usuarioService.listarUsuarios();

        UsuarioAssertions.validarRequisicaoComSucesso(response);
        ApiAssertions.validarTempoRespostaMaxima(response, 2000L);
    }
}
