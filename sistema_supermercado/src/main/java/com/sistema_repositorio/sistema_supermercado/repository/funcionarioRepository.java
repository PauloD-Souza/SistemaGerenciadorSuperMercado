package com.sistema_repositorio.sistema_supermercado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistema_repositorio.sistema_supermercado.model.Funcionarios;

public interface funcionarioRepository extends MongoRepository <Funcionarios, Long> {
    
}
