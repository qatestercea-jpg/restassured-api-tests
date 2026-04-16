package com.qa.factory;

import com.qa.dto.Usuario;

public final class UsuarioFactory {

    private static final String EMAIL_DUPLICADO = "teste.duplicado@qa.com";

    private UsuarioFactory() {
    }

    public static Usuario usuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuário Válido");
        usuario.setEmail("teste" + System.currentTimeMillis() + "@qa.com");
        usuario.setPassword("123456");
        usuario.setAdministrador("true");
        return usuario;
    }

    public static Usuario usuarioSemEmail() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuário Sem Email");
        usuario.setPassword("123456");
        usuario.setAdministrador("true");
        return usuario;
    }

    public static Usuario usuarioComEmailDuplicado() {
        return usuarioComEmailDuplicado(EMAIL_DUPLICADO);
    }

    public static Usuario usuarioComEmailDuplicado(String email) {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuário Duplicado");
        usuario.setEmail(email);
        usuario.setPassword("123456");
        usuario.setAdministrador("true");
        return usuario;
    }
}
