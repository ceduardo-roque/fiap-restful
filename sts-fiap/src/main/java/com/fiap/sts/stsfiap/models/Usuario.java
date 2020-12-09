package com.fiap.sts.stsfiap.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
    // private Long Id;

    @Id
    @Column(name = "id_usuario")
    private String cpf;
    private String senha;

    private String tipo;

    public Usuario() {
        super();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCpf() {
        return cpf;
    }

    // public Long getId() {
    //     return Id;
    // }

    // public void setId(Long id) {
    //     this.Id = id;
    // }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setcpf(String cpf) {
        this.cpf = cpf;
    }

    public Usuario(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
        this.tipo = "1";
    }

    public Usuario(String cpf, String senha, String tipo) {
        this.cpf = cpf;
        this.senha = senha;
        this.tipo = tipo;
    }
}
