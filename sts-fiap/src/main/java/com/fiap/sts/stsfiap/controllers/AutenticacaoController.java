package com.fiap.sts.stsfiap.controllers;

import com.fiap.sts.stsfiap.JwtTokenUtil;
import com.fiap.sts.stsfiap.models.Usuario;
import com.fiap.sts.stsfiap.repositories.UsuariosRepository;
import com.fiap.sts.stsfiap.viewModels.JwtRequest;
import com.fiap.sts.stsfiap.viewModels.JwtResponse;
import com.fiap.sts.stsfiap.viewModels.JwtValidateResponse;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private UsuariosRepository _usuariosRepository;

	public AutenticacaoController(UsuariosRepository usuariosRepository) {
		_usuariosRepository = usuariosRepository;
	}

	@PostMapping()
	public ResponseEntity postMethodName(@RequestBody JwtRequest entity) { // @RequestHeader() String Authorization) {
		// TODO: process POST request
		if (!(entity.clientId.equalsIgnoreCase("123456") && entity.clientSecret.equalsIgnoreCase("13245487")
				&& entity.grantType.equalsIgnoreCase("password"))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!(entity.user == null || entity.user.isEmpty())
				&& !(entity.password == null || entity.password.isEmpty())) {
			Usuario usuario = _usuariosRepository.findFirstByCpf(entity.user);

			if (usuario != null && usuario.getSenha().equals(entity.password)) {

				String token = jwtTokenUtil.generateToken(usuario);

				return ResponseEntity.ok(new JwtResponse(token));
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@GetMapping()
	public ResponseEntity<JwtValidateResponse> getMethodName(@RequestParam String token) {
		if (token != null && !token.isEmpty()) {
			try {
				var cpfUser = jwtTokenUtil.getUsernameFromToken(token);

				if (cpfUser != null && !cpfUser.isEmpty()) {
					var user = _usuariosRepository.findFirstByCpf(cpfUser);
					if (jwtTokenUtil.validateToken(token, user))
						return ResponseEntity.ok().body(new JwtValidateResponse(user.getCpf(), user.getTipo()));
				}
			} catch (SignatureException ex) {
				System.out.println("Assinatura de token inválida :" + ex.getMessage());
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
