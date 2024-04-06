package com.sistema_repositorio.sistema_supermercado.model;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "pagamento")
public class Pagamento {
    @Transient
    public static final String SEQUENCE_NAME = "pagamentos_sequence";
    @Id
    private Long id;
    private String metodoPagamento;
    private BigDecimal valor;
    
    
    
    public Pagamento() {
    }
    
    public Pagamento(String metodoPagamento, BigDecimal valor) {
        this.metodoPagamento = metodoPagamento;
        this.valor = valor;
    }
    
    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}