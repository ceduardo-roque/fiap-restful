package br.com.fiap.wsrest.covidwebapi.security;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import retrofit2.Retrofit;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // private JwtTokenUtil jwtTokenUtil;
    // private JwtUserDetailService userDetailService;

    // public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, JwtUserDetailService
    // userDetailService){
    // this.jwtTokenUtil = jwtTokenUtil;
    // this.userDetailService = userDetailService;
    // }
    @Value("${securityRemote}")
    private String remoteServer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = request.getHeader("Authorization");
        String username = null;
        if (token != null && token.startsWith("Bearer ")) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(remoteServer).build();

            SecurityRemote service = retrofit.create(SecurityRemote.class);
            var resposta = service.validarToken(token.replace("Bearer ", "")).execute();

            var statusCode = resposta.code();
            System.out.println("resposta validacao token ->" + statusCode);

            if (statusCode == 200) {
                Gson gson = new Gson();
                Type type = new TypeToken<SecurityResponse>() {
                }.getType();

                SecurityResponse securityResponse = gson.fromJson(resposta.body().charStream(), type);
                username = securityResponse.username;
            }

        } else {
            logger.warn("Token inexistente ou token não Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "",
                    new ArrayList<>() // roles
            );
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities()); // roles

            userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(userToken);
        }

        filterChain.doFilter(request, response);
    }
}
