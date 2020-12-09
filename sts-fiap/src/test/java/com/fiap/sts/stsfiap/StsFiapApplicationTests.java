package com.fiap.sts.stsfiap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Type;

import com.fiap.sts.stsfiap.models.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.SignatureException;

@SpringBootTest
class StsFiapApplicationTests {

	@Autowired
	private JwtTokenUtil _jwt;

	@Test
	void contextLoads() {
	}

	@Test
	void DeveGerarTokenValido() {
		var token = _jwt.generateToken(new Usuario("USUARIO", "SENHA"));
		assertEquals("USUARIO", _jwt.getUsernameFromToken(token));
	}

	@Test
	void DeveGerarErroTokenComAssinaturaInvalida() {
		assertThrows(SignatureException.class,() ->{ _jwt.getUsernameFromToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");});
	}
}
