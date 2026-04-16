package com.qa.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.BaseTest;
import com.qa.factory.UsuarioFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("API Automation")
@Feature("DTO Validation")
@Tag("dto")
@Tag("contract")
@DisplayName("Validação de serialização e desserialização de DTOs")
public class UsuarioDTOTest extends BaseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Story("Serialização de Usuario")
    @DisplayName("Deve serializar Usuario corretamente para JSON")
    public void deveSerializarUsuarioParaJson() throws JsonProcessingException {
        Usuario usuario = UsuarioFactory.usuarioValido();

        String json = objectMapper.writeValueAsString(usuario);
        JsonNode jsonNode = objectMapper.readTree(json);

        assertAll("Campos de Usuario JSON",
                () -> assertEquals(usuario.getNome(), jsonNode.get("nome").asText()),
                () -> assertEquals(usuario.getEmail(), jsonNode.get("email").asText()),
                () -> assertEquals(usuario.getPassword(), jsonNode.get("password").asText()),
                () -> assertEquals(usuario.getAdministrador(), jsonNode.get("administrador").asText())
        );
    }

    @Test
    @Story("Desserialização de Usuario")
    @DisplayName("Deve desserializar JSON válido para objeto Usuario")
    public void deveDesserializarJsonParaUsuario() throws JsonProcessingException {
        String sourceJson = "{"
                + "\"nome\":\"Usuario DTO\"," 
                + "\"email\":\"dto@qa.com\"," 
                + "\"password\":\"123456\"," 
                + "\"administrador\":\"true\""
                + "}";

        Usuario usuario = objectMapper.readValue(sourceJson, Usuario.class);

        assertAll("Campos de Usuario desserializado",
                () -> assertEquals("Usuario DTO", usuario.getNome()),
                () -> assertEquals("dto@qa.com", usuario.getEmail()),
                () -> assertEquals("123456", usuario.getPassword()),
                () -> assertEquals("true", usuario.getAdministrador())
        );
    }

    @Test
    @DisplayName("Deve garantir que nome e email não são nulos no Usuario válido")
    public void deveValidarCamposObrigatoriosDoUsuario() {
        Usuario usuario = UsuarioFactory.usuarioValido();

        assertAll("Campos obrigatórios de Usuario",
                () -> assertNotNull(usuario.getNome(), "Nome não deve ser nulo"),
                () -> assertNotNull(usuario.getEmail(), "Email não deve ser nulo")
        );
    }

    @Test
    @DisplayName("Deve serializar UsuarioLoginRequest corretamente para JSON")
    public void deveSerializarUsuarioLoginRequestParaJson() throws JsonProcessingException {
        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest("login@qa.com", "senha123");

        String json = objectMapper.writeValueAsString(loginRequest);
        JsonNode jsonNode = objectMapper.readTree(json);

        assertAll("Campos de UsuarioLoginRequest JSON",
                () -> assertEquals("login@qa.com", jsonNode.get("email").asText()),
                () -> assertEquals("senha123", jsonNode.get("password").asText())
        );
    }

    @Test
    @DisplayName("Deve desserializar JSON válido para objeto UsuarioResponse")
    public void deveDesserializarJsonParaUsuarioResponse() throws JsonProcessingException {
        String sourceJson = "{"
                + "\"_id\":\"12345abc\"," 
                + "\"message\":\"Cadastro realizado com sucesso\""
                + "}";

        UsuarioResponse response = objectMapper.readValue(sourceJson, UsuarioResponse.class);

        assertAll("Campos de UsuarioResponse desserializado",
                () -> assertEquals("12345abc", response.getId()),
                () -> assertEquals("Cadastro realizado com sucesso", response.getMessage())
        );
    }
}
