package com.fiap.sts.stsfiap.viewModels;

public class JwtResponse {
    private String AcessToken;

    public String getAcessToken() {
        return AcessToken;
    }


    public JwtResponse(String acessToken) {
        AcessToken = acessToken;
    }
}
