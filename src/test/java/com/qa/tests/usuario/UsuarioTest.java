package com.qa.tests.usuario;

import com.qa.assertions.ApiAssertions;
import com.qa.base.BaseTest;
import com.qa.builder.UsuarioBuilder;
import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioResponse;
import com.qa.factory.UsuarioFactory;
import com.qa.service.UsuarioService;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsuarioTest extends BaseTest {

    private final UsuarioService usuarioService = new UsuarioService(new UsuarioClient());

    private Usuario criarUsuarioValido() {
        return UsuarioFactory.usuarioValido();
    }

    private Response criarUsuarioComSucesso(Usuario usuario) {
        Response response = usuarioService.criarUsuario(usuario);
        ApiAssertions.validarStatus201(response);
        return response;
    }

    private String obterTokenPara(Usuario usuario) {
        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest(usuario.getEmail(), usuario.getPassword());
        return usuarioService.obterToken(loginRequest);
    }

    private void validarRespostaCriacaoUsuario(UsuarioResponse responseDto) {
        assertNotNull(responseDto.getId());
        assertThat(responseDto.getId(), not(isEmptyString()));
        assertThat(responseDto.getMessage(), equalTo("Cadastro realizado com sucesso"));
    }

    @Test
    public void deveCriarUsuarioValido() {

        Usuario usuario = criarUsuarioValido();

        Response createResponse = criarUsuarioComSucesso(usuario);

        createResponse.then()
                .body("message", equalTo("Cadastro realizado com sucesso"));

        UsuarioResponse responseDto = createResponse.as(UsuarioResponse.class);
        validarRespostaCriacaoUsuario(responseDto);
    }

    @Test
    public void deveRetornarTokenAoFazerLoginValido() {

        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        String token = obterTokenPara(usuario);

        assertNotNull(token);
        assertThat(token, not(isEmptyString()));
    }

    @Test
    public void deveListarUsuarios() {

        Usuario usuario = criarUsuarioValido();
        criarUsuarioComSucesso(usuario);

        ApiAssertions.validarStatus200(usuarioService.listarUsuarios())
                .body("usuarios.email", hasItem(usuario.getEmail()));
    }

    @Test
    public void naoDeveCriarUsuarioSemEmail() {

        Usuario usuario = UsuarioFactory.usuarioSemEmail();

        ApiAssertions.validarErroCampo(usuarioService.criarUsuario(usuario), "email", "deve ser uma string");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "usuarioSemArroba", "usuario@"})
    public void naoDeveCriarUsuarioComEmailInvalido(String email) {

        Usuario usuario = UsuarioBuilder.usuarioComEmailInvalido(email).build();

        ApiAssertions.validarErroCampo(usuarioService.criarUsuario(usuario), "email", "email");
    }

    @Test
    public void naoDeveCriarUsuarioDuplicado() {

        Usuario usuario = criarUsuarioValido();

        // cria primeiro
        criarUsuarioComSucesso(usuario);

        // tenta criar duplicado
        usuarioService.criarUsuario(
                UsuarioFactory.usuarioComEmailDuplicado(usuario.getEmail())
        )
        .then()
        .statusCode(400);
    }

    @Test
    public void deveFazerFluxoCompletoUsuario() {

        // 1. Criar usuário
        Usuario usuario = criarUsuarioValido();

        Response createResponse = criarUsuarioComSucesso(usuario);

        String userId = createResponse.jsonPath().getString("_id");

        // 2. Buscar usuário
        ApiAssertions.validarStatus200(usuarioService.listarUsuarios())
                .body("usuarios.email", hasItem(usuario.getEmail()));

        // 3. Deletar usuário
        usuarioService.deletarUsuario(userId)
                .then()
                .statusCode(200);

        // 4. Validar que foi deletado
        ApiAssertions.validarStatus200(usuarioService.listarUsuarios())
                .body("usuarios.email", not(hasItem(usuario.getEmail())));
    }

    @Test
    public void deveCriarUsuarioComBuilderCustomizado() {

        Usuario usuario = UsuarioBuilder.usuarioPersonalizado(
                "Edson QA",
                "teste" + System.currentTimeMillis() + "@qa.com"
        ).build();

        criarUsuarioComSucesso(usuario);
    }
}