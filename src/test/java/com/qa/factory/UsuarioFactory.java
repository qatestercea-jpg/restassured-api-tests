package com.qa.factory;

import com.qa.dto.Usuario;

public final class UsuarioFactory {

    private static final String EMAIL_DUPLICADO = "teste.duplicado@qa.com";

    private UsuarioFactory() {
    }

    public static Usuario usuarioValido() {
        return novoUsuario("Usuário Válido", "teste" + System.currentTimeMillis() + "@qa.com", "true");
    }

    public static Usuario usuarioSemEmail() {
        return novoUsuario("Usuário Sem Email", null, "true");
    }

    public static Usuario usuarioComEmailDuplicado() {
        return usuarioComEmailDuplicado(EMAIL_DUPLICADO);
    }

    public static Usuario usuarioComEmailDuplicado(String email) {
        return novoUsuario("Usuário Duplicado", email, "true");
    }

    public static Usuario usuarioComEmailInvalido(String email) {
        return novoUsuario("Usuário Email Inválido", email, "true");
    }

    public static Usuario usuarioPersonalizado(String nome, String email) {
        return novoUsuario(nome, email, "true");
    }

    public static Usuario usuarioSemSenha() {
        return novoUsuarioBase("Usuário Sem Senha", "teste" + System.currentTimeMillis() + "@qa.com", "true", null);
    }

    public static Usuario usuarioAdministradorInvalido() {
        return novoUsuarioBase("Usuário Administrador Inválido", "teste" + System.currentTimeMillis() + "@qa.com",
                "invalido", "123456");
    }

    public static Usuario usuarioComCamposVazios() {
        return novoUsuarioBase("", "", "", "");
    }

    public static Usuario usuarioDuplicado() {
        return novoUsuarioBase("Usuário Duplicado", EMAIL_DUPLICADO, "true", "123456");
    }

    private static Usuario novoUsuario(String nome, String email, String administrador) {
        return novoUsuarioBase(nome, email, administrador, "123456");
    }

    private static Usuario novoUsuarioBase(String nome, String email, String administrador, String password) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        if (email != null) {
            usuario.setEmail(email);
        }
        usuario.setPassword(password);
        usuario.setAdministrador(administrador);
        return usuario;
    }
}
