package com.qa.tests.usuario;

import com.qa.base.BaseTest;
import com.qa.builder.UsuarioBuilder;
import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.dto.UsuarioResponse;
import com.qa.service.UsuarioService;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsuarioTest extends BaseTest {

    private static final UsuarioClient USUARIO_CLIENT = new UsuarioClient();

    private Usuario criarUsuarioValido() {
        return UsuarioBuilder.usuarioValido().build();
    }

    private void validarRespostaCriacaoUsuario(UsuarioResponse responseDto) {
        assertNotNull(responseDto.getId());
        assertThat(responseDto.getId(), not(isEmptyString()));
        assertThat(responseDto.getMessage(), equalTo("Cadastro realizado com sucesso"));
    }

    @Test
    public void deveCriarUsuarioValido() {

        Usuario usuario = UsuarioBuilder.usuarioValido().build();

        Response createResponse = UsuarioService.criarUsuario(usuario);

        createResponse.then()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"));

        UsuarioResponse responseDto = createResponse.as(UsuarioResponse.class);
        validarRespostaCriacaoUsuario(responseDto);
    }

    @Test
    public void deveRetornarTokenAoFazerLoginValido() {

        Usuario usuario = UsuarioBuilder.usuarioValido().build();
        UsuarioService.criarUsuario(usuario).then().statusCode(201);

        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest(usuario.getEmail(), usuario.getPassword());
        String token = UsuarioService.loginRetornandoToken(loginRequest);

        assertNotNull(token);
        assertThat(token, not(isEmptyString()));
    }

    @Test
    public void deveListarUsuarios() {

        UsuarioService.listarUsuarios()
                .then()
                .statusCode(200)
                .body("usuarios.size()", greaterThan(0));
    }

    @Test
    public void naoDeveCriarUsuarioSemEmail() {

        Usuario usuario = UsuarioBuilder.usuarioSemEmail().build();

        UsuarioService.criarUsuario(usuario)
                .then()
                .statusCode(400)
                .body("email", containsString("deve ser uma string"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "usuarioSemArroba", "usuario@"})
    public void naoDeveCriarUsuarioComEmailInvalido(String email) {

        Usuario usuario = UsuarioBuilder.umUsuario()
                .comNome("Usuário Email Inválido")
                .comEmail(email)
                .comPassword("123456")
                .admin()
                .build();

        Response response = USUARIO_CLIENT.criarUsuario(usuario);

        response.then()
                .statusCode(400)
                .body("email", containsString("email"));
    }

    @Test
    public void naoDeveCriarUsuarioDuplicado() {

        Usuario usuario = UsuarioBuilder.usuarioValido().build();

        // cria primeiro
        UsuarioService.criarUsuario(usuario)
                .then()
                .statusCode(201);

        // tenta criar duplicado
        UsuarioService.criarUsuario(
                UsuarioBuilder.usuarioEmailDuplicado(usuario.getEmail()).build()
        )
        .then()
        .statusCode(400);
    }

    @Test
    public void deveFazerFluxoCompletoUsuario() {

        // 1. Criar usuário
        Usuario usuario = UsuarioBuilder.usuarioValido().build();

        Response createResponse = UsuarioService.criarUsuario(usuario);

        createResponse.then()
                .statusCode(201);

        String userId = createResponse.jsonPath().getString("_id");

        // 2. Buscar usuário
        UsuarioService.listarUsuarios()
                .then()
                .statusCode(200)
                .body("usuarios.email", hasItem(usuario.getEmail()));

        // 3. Deletar usuário
        UsuarioService.deletarUsuario(userId)
                .then()
                .statusCode(200);

        // 4. Validar que foi deletado
        UsuarioService.listarUsuarios()
                .then()
                .statusCode(200)
                .body("usuarios.email", not(hasItem(usuario.getEmail())));
    }

    @Test
    public void deveCriarUsuarioComBuilderCustomizado() {

        Usuario usuario = UsuarioBuilder.umUsuario()
                .comNome("Edson QA")
                .comEmail("teste" + System.currentTimeMillis() + "@qa.com")
                .comPassword("123456")
                .admin()
                .build();

        UsuarioService.criarUsuario(usuario)
                .then()
                .statusCode(201);
    }
}