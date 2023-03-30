package com.sistema_repositorio.sistema_supermercado.service;


public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(Long id) {
      super("Cliente não encontrado com o ID: " + id);
    }
  }
  
