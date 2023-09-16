package com.sistema_repositorio.sistema_supermercado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistema_repositorio.sistema_supermercado.model.Carrinho;

public interface carrinhoRepository extends MongoRepository <Carrinho,Long> {
    
}
