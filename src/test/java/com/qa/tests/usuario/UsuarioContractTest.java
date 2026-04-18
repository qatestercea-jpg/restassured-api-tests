package com.qa.tests.usuario;

import com.qa.assertions.ApiAssertions;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@Epic("API Automation")
@Feature("Contrato")
@Tag("contract")
@Tag("regression")
@DisplayName("Contrato de API de usuário")
public class UsuarioContractTest extends UsuarioTestBase {

    @Test
    @Story("Validação de contrato da criação de usuário")
    @DisplayName("Deve validar campos obrigatórios da resposta de criação de usuário")
    public void deveValidarContratoRespostaCriacaoUsuario() {
        Usuario usuario = criarUsuarioValido();

        Response response = criarUsuarioComSucesso(usuario);
        UsuarioResponse responseDto = response.as(UsuarioResponse.class);

        assertThat(responseDto.getId(), not(isEmptyString()));
        assertThat(responseDto.getMessage(), equalTo("Cadastro realizado com sucesso"));
        response.then().body("_id", notNullValue());
        response.then().body("message", equalTo("Cadastro realizado com sucesso"));
    }
}
