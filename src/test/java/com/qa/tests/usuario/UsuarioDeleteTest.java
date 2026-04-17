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
        String userId = criarUsuarioERetornarId(usuario);

        UsuarioAssertions.validarListagemContemEmail(usuarioService.listarUsuarios(), usuario.getEmail());

        UsuarioAssertions.validarDelecaoComSucesso(usuarioService.deletarUsuario(userId));

        UsuarioAssertions.validarListagemNaoContemEmail(usuarioService.listarUsuarios(), usuario.getEmail());
    }
}
