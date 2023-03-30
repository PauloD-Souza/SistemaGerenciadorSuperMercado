package com.sistema_repositorio.sistema_supermercado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sistema_repositorio.sistema_supermercado.model.produto;
import com.sistema_repositorio.sistema_supermercado.repository.produtoRepository;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGenerator;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@Validated
public class produtosController {
    
    @Autowired
    private produtoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<Object> listarClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(this.produtoRepository.findAll());
    }
    @PostMapping
    public ResponseEntity<Object> adicionarProduto(@Valid @RequestBody produto p) {
        p.setId(sequenceGenerator.generateSequence(produto.SEQUENCE_NAME));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.produtoRepository.save(p));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable long id) {
        Optional<produto> p = this.produtoRepository.findById(id);

        if (p.isPresent()) {
            this.produtoRepository.delete(p.get());
            return ResponseEntity.status(HttpStatus.OK).body("Produto" + p.get().getNome() +""+ " excluido com sucesso.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado na base de dados");
    }
    
}
