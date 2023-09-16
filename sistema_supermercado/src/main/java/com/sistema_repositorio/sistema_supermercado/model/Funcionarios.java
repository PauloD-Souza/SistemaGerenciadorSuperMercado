package com.sistema_repositorio.sistema_supermercado.model;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class Funcionarios{

    @Transient
    public static final String SEQUENCE_NAME = "funcionarios_sequence";
    
    @Id
    private long id;

    @NotEmpty @NotNull
    private String nome;

    @NotEmpty @NotNull
    private String salario;

    @NotEmpty @NotNull
    private String registroCarteiraTrabalho;

    @CPF
    private String cpf;

    @Email
    private String email;

    

}

