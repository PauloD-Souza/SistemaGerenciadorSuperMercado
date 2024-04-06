package com.sistema_repositorio.sistema_supermercado.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection = "Carrinho")
public class Carrinho {
    @Transient
    public static final String SEQUENCE_NAME = "carrinho_sequence";
    
    @Id
    private long id;

    @NotNull
    private List<Produto> produtos;

    public Carrinho() {
        this.produtos = new ArrayList<>();
    }



    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Produto produto : produtos) {
            total = total.add(produto.getPrice());
        }
        return total;
    }
}

