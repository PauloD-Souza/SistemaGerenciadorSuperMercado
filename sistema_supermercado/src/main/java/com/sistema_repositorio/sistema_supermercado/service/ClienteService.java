package com.sistema_repositorio.sistema_supermercado.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
  @Service
public class ClienteService {
  @Autowired
  private ClienteRepository clienteRepository;

  public List<Cliente> buscarTodos() {
    return clienteRepository.findAll();
  }

  public Cliente buscarPorId(Long id) {
    return clienteRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
  }

  public Cliente salvar(Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  public void deletar(Long id) {
    Cliente cliente = buscarPorId(id);
    clienteRepository.delete(cliente);
  }

  public Cliente atualizar(Long id, Cliente cliente) {
    buscarPorId(cliente.getId());
    return clienteRepository.save(cliente);
  }
}
