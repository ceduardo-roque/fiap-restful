package com.fiap.sts.stsfiap.viewModels;

import java.util.List;

public class JwtValidateResponse {
    private String username;
    public List<String> rules;
    private String tipo;

    public JwtValidateResponse(String username, String tipo) {
        this.username = username;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

}
