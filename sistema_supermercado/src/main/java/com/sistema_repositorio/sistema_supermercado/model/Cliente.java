package com.sistema_repositorio.sistema_supermercado.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Data;


@Data
public class Cliente {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
    
    @Id
    private long id;

    private String nome;
    private String cpf;
    private String email;


}
