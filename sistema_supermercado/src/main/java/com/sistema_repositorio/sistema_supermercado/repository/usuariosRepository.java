package com.sistema_repositorio.sistema_supermercado.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sistema_repositorio.sistema_supermercado.model.Usuarios;

public interface usuariosRepository extends MongoRepository <Usuarios, Long> {
    Optional<Usuarios> findByUsername(String username);
    Optional<Usuarios> existsByUsername(String username);
}
