package com.qa.dto;

import java.util.List;

public class UsuarioListResponse {

    private List<Usuario> usuarios;

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "UsuarioListResponse{" +
                "usuarios=" + usuarios +
                '}';
    }
}
