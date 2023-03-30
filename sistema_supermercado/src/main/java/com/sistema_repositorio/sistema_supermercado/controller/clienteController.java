package com.sistema_repositorio.sistema_supermercado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/Clientes")
@Validated
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> buscarTodos() {
        return clienteService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Validated @RequestBody Cliente cliente) {
        if (!Cliente.validarCPF(cliente.getCpf())) {
            throw new RuntimeException("CPF inválido");
        }
    
        if (!Cliente.validarEmail(cliente.getEmail())) {
            throw new RuntimeException("E-mail inválido");
        }
    
        Cliente novoCliente = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienteService.deletar(id);
    }

    @PutMapping("/{id}")
public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Validated @RequestBody Cliente clienteAtualizado) {
    Cliente clienteExistente = clienteService.buscarPorId(id);
    clienteExistente.setName(clienteAtualizado.getName());
    clienteExistente.setCpf(clienteAtualizado.getCpf());
    clienteExistente.setEmail(clienteAtualizado.getEmail());

    if (!Cliente.validarCPF(clienteExistente.getCpf())) {
        throw new RuntimeException("CPF inválido");
    }

    if (!Cliente.validarEmail(clienteExistente.getEmail())) {
        throw new RuntimeException("E-mail inválido");
    }

    Cliente clienteAtualizadoSalvo = clienteService.atualizar(id, clienteExistente);
    return ResponseEntity.ok(clienteAtualizadoSalvo);
}

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> atualizarParcial(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteService.buscarPorId(id);

        if (clienteAtualizado.getName() != null) {
            clienteExistente.setName(clienteAtualizado.getName());
        }

        if (clienteAtualizado.getCpf() != null) {
            if (!Cliente.validarCPF(clienteAtualizado.getCpf())) {
                throw new IllegalArgumentException("CPF inválido");
            }
            clienteExistente.setCpf(clienteAtualizado.getCpf());
        }

        if (clienteAtualizado.getEmail() != null) {
            if (!Cliente.validarEmail(clienteAtualizado.getEmail())) {
                throw new IllegalArgumentException("E-mail inválido");
            }
            clienteExistente.setEmail(clienteAtualizado.getEmail());
        }

        clienteService.atualizar(id, clienteExistente);

        return ResponseEntity.ok(clienteExistente);
    }

}
