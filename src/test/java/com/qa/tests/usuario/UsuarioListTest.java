package com.qa.tests.usuario;

import com.qa.assertions.ApiAssertions;
import com.qa.assertions.UsuarioAssertions;
import com.qa.dto.Usuario;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItem;

@Epic("API Automation")
@Feature("Usuário")
@Tag("usuario")
@Tag("performance")
@Tag("regression")
@Tag("smoke")
@DisplayName("Listagem de usuários")
public class UsuarioListTest extends UsuarioTestBase {

    @Test
    @Story("Consistência de listagem após criação")
    @Description("Verifica que o usuário criado aparece na listagem de usuários e que a resposta segue o schema esperado")
    @DisplayName("Usuário criado deve aparecer na listagem")
    public void deveExibirUsuarioCriadoNaListagem() {
        Usuario usuario = criarUsuarioValido();
        usuarioService.criarUsuarioERetornarId(usuario);

        Response response = usuarioService.listarUsuarios();
        UsuarioAssertions.validarListagemUsuarios(response, usuario.getEmail())
                .body(matchesJsonSchemaInClasspath("schema/list-user-schema.json"));

        ApiAssertions.validarEsquema(response, "schema/list-user-schema.json");
    }

    @Test
    @Story("Consulta de usuários cadastrados")
    @DisplayName("Deve listar usuário cadastrado")
    public void deveListarUsuarioCadastrado() {
        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        UsuarioAssertions.validarListagemUsuarios(usuarioService.listarUsuarios(), usuario.getEmail());
    }

    @Test
    @Story("Performance de listagem de usuários")
    @DisplayName("Deve retornar listagem dentro do tempo esperado")
    public void deveListarUsuariosDentroDoTempoMaximo() {
        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        Response response = usuarioService.listarUsuarios();

        UsuarioAssertions.validarListagemUsuarios(response);
        ApiAssertions.validarTempoRespostaMaxima(response, 2000L);
    }
}
