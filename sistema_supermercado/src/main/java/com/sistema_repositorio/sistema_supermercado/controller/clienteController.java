package com.sistema_repositorio.sistema_supermercado.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;


import com.sistema_repositorio.sistema_supermercado.model.Cliente;
import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.repository.clienteRepository;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGeneratorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")

@Validated
public class clienteController {

    @Autowired
    private sequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private clienteRepository clienteRepository;
    
    @GetMapping
    public ResponseEntity<Object> listarClientes() {
    
        List<Cliente> clientes = this.clienteRepository.findAll();
        
        if (!clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(clientes);
        } else {
            // Se a lista de clientes estiver vazia, retorne uma mensagem de erro com status 404 (Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum cliente encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<Object> adicionarCliente(@Valid @RequestBody Cliente c) {
        c.setId(sequenceGeneratorService.generateSequence(Cliente.SEQUENCE_NAME));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clienteRepository.save(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable long id) {
        Optional<Cliente> c = this.clienteRepository.findById(id);

        if (c.isPresent()) {
            this.clienteRepository.delete(c.get());
            return ResponseEntity.status(HttpStatus.OK).body("Cliente " + c.get().getNome() + " excluido com sucesso.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado na base de dados");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> corrigirCliente(@Valid @PathVariable long id, @RequestBody Cliente c) {
        Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(id);

        if (clienteEncontrado.isPresent()) {
            clienteEncontrado.get().setEmail(c.getEmail());
            this.clienteRepository.save(clienteEncontrado.get());
            return ResponseEntity.status(HttpStatus.OK).body("o cliente " + clienteEncontrado.get().getNome()
                    + " teve seu email atualizado para " + clienteEncontrado.get().getEmail());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado na base de dados");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCliente(@Valid @PathVariable long id, @RequestBody Cliente c) {

        Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(id);
        if (clienteEncontrado.isPresent()) {
            clienteEncontrado.get().setCpf(c.getCpf());
            clienteEncontrado.get().setEmail(c.getEmail());
            clienteEncontrado.get().setNome(c.getNome());
            this.clienteRepository.save(clienteEncontrado.get());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Novos dados do cliente: " + clienteEncontrado.get().toString());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("ID do cliente não encontrado em nosso banco de dados.\nID: " + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarClientePorId(@PathVariable long id) {

        Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(id);

        if (clienteEncontrado.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(this.clienteRepository.findById(id));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("ID do cliente não encontrado no banco de dados\nID: " + id);
    }

    @GetMapping("/cpf")
    public ResponseEntity<Object> buscarClientePorCPF(@RequestParam(value = "searchCpf", required = true) String cpf) {
        Cliente clienteEncontrado = null;
        for (Cliente c : this.clienteRepository.findAll()) {
            if (c.getCpf().equals(cpf)) {
                clienteEncontrado = c;
                return ResponseEntity.status(HttpStatus.OK).body(clienteEncontrado);
            }

        }
        ErroResponse erroResponse = new ErroResponse("Nenhum cliente encontrado com as informações fonecidas");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
    }

    @GetMapping("/nome")
    public ResponseEntity<Object> buscarClientePorNome(@RequestParam(value = "searchNome", required = true) String nome) {
        List<Cliente> clientesEncontrados = new ArrayList<>();

        for(Cliente c : this.clienteRepository.findAll()){
            if(containsIgnoreCase(c.getNome(), nome)){
                clientesEncontrados.add(c);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(clientesEncontrados);
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    
}


