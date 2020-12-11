package br.com.fiap.wsrest.covidwebapi.security;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SecurityRemote {
    @GET("autenticacao")
    Call<ResponseBody> validarToken(@Query("token") String sort);
}
