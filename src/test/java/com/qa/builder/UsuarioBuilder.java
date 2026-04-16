package com.qa.builder;

import com.qa.dto.Usuario;

public class UsuarioBuilder {

    private final Usuario usuario = new Usuario();

    public UsuarioBuilder() {
    }

    public static UsuarioBuilder umUsuario() {
        return new UsuarioBuilder();
    }

    public UsuarioBuilder comNome(String nome) {
        usuario.setNome(nome);
        return this;
    }

    public UsuarioBuilder comEmail(String email) {
        usuario.setEmail(email);
        return this;
    }

    public UsuarioBuilder comPassword(String password) {
        usuario.setPassword(password);
        return this;
    }

    public UsuarioBuilder admin() {
        usuario.setAdministrador("true");
        return this;
    }

    public UsuarioBuilder semAdmin() {
        usuario.setAdministrador("false");
        return this;
    }

    public Usuario build() {
        return usuario;
    }

    public static UsuarioBuilder usuarioValido() {
        return umUsuario()
                .comNome("Usuário Válido")
                .comEmail("teste" + System.currentTimeMillis() + "@qa.com")
                .comPassword("123456")
                .admin();
    }

    public static UsuarioBuilder usuarioSemEmail() {
        return umUsuario()
                .comNome("Usuário Sem Email")
                // NÃO chama .comEmail()
                .comPassword("123456")
                .admin();
    }

    public static UsuarioBuilder usuarioEmailDuplicado(String email) {
        return umUsuario()
                .comNome("Usuário Duplicado")
                .comEmail(email)
                .comPassword("123456")
                .admin();
    }

    public static UsuarioBuilder usuarioComEmailInvalido(String email) {
        return umUsuario()
                .comNome("Usuário Email Inválido")
                .comEmail(email)
                .comPassword("123456")
                .admin();
    }

    public static UsuarioBuilder usuarioPersonalizado(String nome, String email) {
        return umUsuario()
                .comNome(nome)
                .comEmail(email)
                .comPassword("123456")
                .admin();
    }
}