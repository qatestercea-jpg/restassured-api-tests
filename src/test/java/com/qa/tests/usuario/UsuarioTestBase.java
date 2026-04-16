package com.qa.tests.usuario;

import com.qa.base.BaseTest;
import com.qa.client.UsuarioClient;
import com.qa.dto.Usuario;
import com.qa.dto.UsuarioLoginRequest;
import com.qa.factory.UsuarioFactory;
import com.qa.service.UsuarioService;
import io.restassured.response.Response;

public abstract class UsuarioTestBase extends BaseTest {

    protected final UsuarioService usuarioService = new UsuarioService(new UsuarioClient());

    protected Usuario criarUsuarioValido() {
        return UsuarioFactory.usuarioValido();
    }

    protected Response criarUsuarioComSucesso(Usuario usuario) {
        Response response = usuarioService.criarUsuario(usuario);
        com.qa.assertions.ApiAssertions.validarStatus201(response);
        return response;
    }

    protected String obterTokenPara(Usuario usuario) {
        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest(usuario.getEmail(), usuario.getPassword());
        return usuarioService.obterToken(loginRequest);
    }
}
