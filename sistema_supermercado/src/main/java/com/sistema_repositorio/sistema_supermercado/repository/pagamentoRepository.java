package com.sistema_repositorio.sistema_supermercado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistema_repositorio.sistema_supermercado.model.Pagamento;


public interface pagamentoRepository extends MongoRepository <Pagamento, Long>{
    
}