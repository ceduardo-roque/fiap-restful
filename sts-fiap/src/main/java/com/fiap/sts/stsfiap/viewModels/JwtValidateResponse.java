package com.fiap.sts.stsfiap.viewModels;

import java.util.List;

public class JwtValidateResponse {
    private String username;
    public List<String> rules;

    public JwtValidateResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
