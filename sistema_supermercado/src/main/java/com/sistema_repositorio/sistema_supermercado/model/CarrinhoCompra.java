package com.sistema_repositorio.sistema_supermercado.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carrinhos")

public class CarrinhoCompra {
    @Id
    private String id;

    private List<produto> itens = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<produto> getItens() {
        return itens;
    }

    public void setItens(List<produto> itens) {
        this.itens = itens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    /* 
    public void adicionarItem(produto item) {
        itens.add(item);
        item.setCarrinho(this);
        atualizarTotal();
    }

    public void removerItem(produto item) {
        itens.remove(item);
        item.setCarrinho(null);
        atualizarTotal();
    }

    private void atualizarTotal() {
        total = itens.stream().map(produto::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    */
}

