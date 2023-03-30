package com.sistema_repositorio.sistema_supermercado.model;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
public class produto {
    
    @Id
    private long id;

    @NotEmpty @NotNull
    private String nome;
    
    private String Descricao;

    private BigDecimal price;
    
}
