package com.fiap.sts.stsfiap.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fiap.sts.stsfiap.models.Usuario;
import com.fiap.sts.stsfiap.repositories.UsuariosRepository;
import com.fiap.sts.stsfiap.viewModels.UserPassRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// @RestController
// @RequestMapping("password")
// public class PasswordController {

//     private UsuariosRepository _usuariosRepository;

//     public PasswordController(UsuariosRepository usuariosRepository) {
//         super();
//         _usuariosRepository = usuariosRepository;
//     }

//     @PostMapping()
//     public ResponseEntity postMethodName(@RequestBody UserPassRequest entity) {

//         if (entity != null && !entity.password.isEmpty() && !entity.cpf.isEmpty()) {
//             _usuariosRepository.save(new Usuario(entity.cpf, entity.password));
//             return ResponseEntity.status(HttpStatus.CREATED).build();
//         }

//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//     }

// }
