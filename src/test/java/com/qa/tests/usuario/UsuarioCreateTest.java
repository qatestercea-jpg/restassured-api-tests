package com.qa.tests.usuario;

import com.qa.assertions.UsuarioAssertions;
import com.qa.dto.Usuario;
import com.qa.factory.UsuarioFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@Epic("API Automation")
@Feature("Usuário")
@Tag("usuario")
@Tag("regression")
@Tag("smoke")
@DisplayName("Criação de usuário")
public class UsuarioCreateTest extends UsuarioTestBase {

    @Test
    @Story("Cadastro de usuário válido")
    @Description("Valida criação de usuário e contrato de resposta JSON contra o schema de usuário")
    @DisplayName("Deve criar usuário válido com sucesso")
    public void deveCriarUsuarioComDadosValidos() {
        Usuario usuario = criarUsuarioValido();

        Response createResponse = criarUsuarioComSucesso(usuario);

        UsuarioAssertions.validarUsuarioCriado(createResponse)
                .body("message", equalTo("Cadastro realizado com sucesso"))
                .body(matchesJsonSchemaInClasspath("schema/create-user-schema.json"));

        UsuarioAssertions.validarEsquemaUsuario(createResponse);
    }

    @Test
    @DisplayName("Deve retornar erro ao criar usuário sem email")
    public void deveRetornarErroAoCriarUsuarioSemEmail() {
        UsuarioAssertions.validarErroCampo(usuarioService.criarUsuarioInvalido(), "email", "deve ser uma string");
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "usuarioSemArroba", "usuario@" })
    @DisplayName("Deve rejeitar emails inválidos no cadastro")
    public void deveRetornarErroAoCriarUsuarioComEmailInvalido(String email) {
        Usuario usuario = UsuarioFactory.usuarioComEmailInvalido(email);

        UsuarioAssertions.validarErroCampo(usuarioService.criarUsuario(usuario), "email", "email");
    }

    @Test
    @DisplayName("Deve impedir cadastro com email duplicado")
    public void deveRetornarErroAoCriarUsuarioComEmailDuplicado() {
        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        UsuarioAssertions.validarErroDeCriacao(usuarioService.criarUsuario(
                UsuarioFactory.usuarioComEmailDuplicado(usuario.getEmail())));
    }

    @Test
    @DisplayName("Deve criar usuário com dados personalizados")
    public void deveCriarUsuarioComDadosPersonalizados() {
        Usuario usuario = UsuarioFactory.usuarioPersonalizado(
                "Edson QA",
                "teste" + System.currentTimeMillis() + "@qa.com");

        criarUsuarioComSucesso(usuario);
    }
}
