package com.fiap.sts.stsfiap.repositories;

import com.fiap.sts.stsfiap.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
    Usuario findFirstByCpf(String cpf);
}
