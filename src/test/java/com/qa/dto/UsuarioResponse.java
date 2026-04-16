package com.qa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioResponse {

    @JsonProperty("_id")
    private String id;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UsuarioResponse{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
