package com.sistema_repositorio.sistema_supermercado.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Carrinho {
    @Transient
    public static final String SEQUENCE_NAME = "carrinho_sequence_name";
    
    @Id
    private long id;

    @NotEmpty
    private String nomeCliente;

    @NotNull
    private List<produto> produtos;

    private double total;

    private List<produto> itens;

}
