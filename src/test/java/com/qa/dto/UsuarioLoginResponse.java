package com.qa.dto;

public class UsuarioLoginResponse {

    private String authorization;
    private String message;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UsuarioLoginResponse{" +
                "authorization='" + authorization + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
