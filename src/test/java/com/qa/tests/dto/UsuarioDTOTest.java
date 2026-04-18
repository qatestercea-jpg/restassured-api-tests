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
@Tag("regression")
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
                                () -> assertEquals(usuario.getAdministrador(), jsonNode.get("administrador").asText()),
                                () -> assertTrue(jsonNode.get("nome").isTextual(), "nome deve ser string"),
                                () -> assertTrue(jsonNode.get("email").isTextual(), "email deve ser string"),
                                () -> assertTrue(jsonNode.get("password").isTextual(), "password deve ser string"),
                                () -> assertTrue(jsonNode.get("administrador").isTextual(),
                                                "administrador deve ser string"));
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
                                () -> assertEquals("true", usuario.getAdministrador()));
        }

        @Test
        @DisplayName("Deve garantir que nome, email, password e administrador não são nulos no Usuario válido")
        public void deveValidarCamposObrigatoriosDoUsuario() {
                Usuario usuario = UsuarioFactory.usuarioValido();

                assertAll("Campos obrigatórios de Usuario",
                                () -> assertNotNull(usuario.getNome(), "Nome não deve ser nulo"),
                                () -> assertNotNull(usuario.getEmail(), "Email não deve ser nulo"),
                                () -> assertNotNull(usuario.getPassword(), "Password não deve ser nulo"),
                                () -> assertNotNull(usuario.getAdministrador(), "Administrador não deve ser nulo"));
        }

        @Test
        @DisplayName("Deve validar campos obrigatórios do UsuarioLoginRequest")
        public void deveValidarCamposObrigatoriosDoUsuarioLoginRequest() {
                UsuarioLoginRequest loginRequest = new UsuarioLoginRequest("login@qa.com", "senha123");

                assertAll("Campos obrigatórios de UsuarioLoginRequest",
                                () -> assertNotNull(loginRequest.getEmail(), "Email não deve ser nulo"),
                                () -> assertNotNull(loginRequest.getPassword(), "Password não deve ser nulo"));
        }

        @Test
        @DisplayName("Deve validar campos obrigatórios do UsuarioResponse")
        public void deveValidarCamposObrigatoriosDoUsuarioResponse() {
                UsuarioResponse response = new UsuarioResponse();
                response.setId("12345abc");
                response.setMessage("Cadastro realizado com sucesso");

                assertAll("Campos obrigatórios de UsuarioResponse",
                                () -> assertNotNull(response.getId(), "Id não deve ser nulo"),
                                () -> assertNotNull(response.getMessage(), "Message não deve ser nulo"));
        }

        @Test
        @DisplayName("Deve serializar UsuarioLoginRequest corretamente para JSON")
        public void deveSerializarUsuarioLoginRequestParaJson() throws JsonProcessingException {
                UsuarioLoginRequest loginRequest = new UsuarioLoginRequest("login@qa.com", "senha123");

                String json = objectMapper.writeValueAsString(loginRequest);
                JsonNode jsonNode = objectMapper.readTree(json);

                assertAll("Campos de UsuarioLoginRequest JSON",
                                () -> assertEquals("login@qa.com", jsonNode.get("email").asText()),
                                () -> assertEquals("senha123", jsonNode.get("password").asText()),
                                () -> assertTrue(jsonNode.get("email").isTextual(), "email deve ser string"),
                                () -> assertTrue(jsonNode.get("password").isTextual(), "password deve ser string"));
        }

        @Test
        @DisplayName("Deve desserializar JSON válido para objeto UsuarioLoginRequest")
        public void deveDesserializarJsonParaUsuarioLoginRequest() throws JsonProcessingException {
                String sourceJson = "{"
                                + "\"email\":\"login@qa.com\","
                                + "\"password\":\"senha123\""
                                + "}";

                UsuarioLoginRequest loginRequest = objectMapper.readValue(sourceJson, UsuarioLoginRequest.class);

                assertAll("Campos de UsuarioLoginRequest desserializado",
                                () -> assertEquals("login@qa.com", loginRequest.getEmail()),
                                () -> assertEquals("senha123", loginRequest.getPassword()));
        }

        @Test
        @DisplayName("Deve serializar UsuarioResponse corretamente para JSON")
        public void deveSerializarUsuarioResponseParaJson() throws JsonProcessingException {
                UsuarioResponse response = new UsuarioResponse();
                response.setId("12345abc");
                response.setMessage("Cadastro realizado com sucesso");

                String json = objectMapper.writeValueAsString(response);
                JsonNode jsonNode = objectMapper.readTree(json);

                assertAll("Campos de UsuarioResponse JSON",
                                () -> assertEquals("12345abc", jsonNode.get("_id").asText()),
                                () -> assertEquals("Cadastro realizado com sucesso", jsonNode.get("message").asText()),
                                () -> assertTrue(jsonNode.get("_id").isTextual(), "_id deve ser string"),
                                () -> assertTrue(jsonNode.get("message").isTextual(), "message deve ser string"));
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
                                () -> assertEquals("Cadastro realizado com sucesso", response.getMessage()));
        }
}
