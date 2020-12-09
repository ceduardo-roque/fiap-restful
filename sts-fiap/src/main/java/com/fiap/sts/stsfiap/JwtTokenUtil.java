package com.fiap.sts.stsfiap;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.fiap.sts.stsfiap.models.Usuario;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 30 * 30;

    @Value("${jwt.secret}")
    private String secret;

    // retorna o username do token jwt
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retorna expiration date do token jwt
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // gera token para user
    public String generateToken(Usuario Usuario) {
        Map<String, Object> claims = new HashMap<>();
        
        claims.put("tipo", Usuario.getTipo());

        return doGenerateToken(claims, Usuario.getCpf());
    }

    // Cria o token e devine tempo de expiração pra ele
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
    }

    // valida o token
    public Boolean validateToken(String token, Usuario Usuario) {
        final String username = getUsernameFromToken(token);
        return (username.equals(Usuario.getCpf()) && !isTokenExpired(token));
    }

}
