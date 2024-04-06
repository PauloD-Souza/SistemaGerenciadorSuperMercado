package com.sistema_repositorio.sistema_supermercado.model;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "Produtos")
public class Produto {

    @Transient
    public static final String SEQUENCE_NAME = "products_sequence";
    
    @Id
    private long id;

    @NotEmpty @NotNull
    private String nome;
    
    private String Descricao;

    private BigDecimal price;
    
    public BigDecimal getPrice() {
        return price;
    }
    
}
