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
    @Description("Verifica exclusão de usuário criado e garante que não exista mais na listagem")
    @DisplayName("Deve excluir usuário criado com sucesso")
    public void deveExcluirUsuarioCriadoComSucesso() {
        Usuario usuario = criarUsuarioValido();
        String userId = usuarioService.criarUsuarioERetornarId(usuario);

        UsuarioAssertions.validarListagemContemEmail(usuarioService.listarUsuarios(), usuario.getEmail());

        Response deleteResponse = usuarioService.deletarUsuarioComValidacao(userId);
        UsuarioAssertions.validarUsuarioDeletado(deleteResponse);

        UsuarioAssertions.validarListagemNaoContemEmail(usuarioService.listarUsuarios(), usuario.getEmail());
    }

    @Test
    @Story("Idempotência de exclusão")
    @Description("Verifica comportamento de exclusão repetida para o mesmo usuário")
    @DisplayName("Deletar o mesmo usuário duas vezes deve ser idempotente")
    public void devePermitirExcluirMesmoUsuarioDuasVezes() {
        Usuario usuario = criarUsuarioValido();
        String userId = usuarioService.criarUsuarioERetornarId(usuario);

        UsuarioAssertions.validarUsuarioDeletado(usuarioService.deletarUsuarioComValidacao(userId));

        Response secondDeletion = usuarioService.deletarUsuario(userId);
        if (secondDeletion.getStatusCode() == 200) {
            UsuarioAssertions.validarUsuarioDeletado(secondDeletion);
        } else {
            ApiAssertions.validarStatus(secondDeletion, 404);
        }
    }
}
