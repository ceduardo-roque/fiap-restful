package com.fiap.sts.stsfiap;

import com.fiap.sts.stsfiap.models.Usuario;
import com.fiap.sts.stsfiap.repositories.UsuariosRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerBean implements ApplicationRunner {
    private UsuariosRepository _usuariosRepository;

    public ApplicationRunnerBean(UsuariosRepository usuariosRepository) {
        super();
        _usuariosRepository = usuariosRepository;
    }

    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        System.out.println("INICIANDO ");
        _usuariosRepository.save(new Usuario("123456", "121294"));
        // org.h2.tools.Server server = org.h2.tools.Server.createTcpServer().start();
        
    }
}
