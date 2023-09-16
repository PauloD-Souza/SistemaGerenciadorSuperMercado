package com.sistema_repositorio.sistema_supermercado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema_repositorio.sistema_supermercado.model.Carrinho;
import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.repository.carrinhoRepository;

@RestController
@RequestMapping("/carrinho")
public class carrinhoController {
    
    @Autowired
    private final carrinhoRepository carrinhoRepository;

    public carrinhoController(carrinhoRepository carrinhoRepository) {
        this.carrinhoRepository = carrinhoRepository;
    }

    @GetMapping("/listarProdutosNoCarrinho")
    public ResponseEntity<Object> listarProdutosNoCarrinho() {
        long count = carrinhoRepository.count();
        if (count == 0) {
            ErroResponse erroResponse = new ErroResponse("O carrinho está vazio");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
        } else {
            List<Carrinho> produtos = carrinhoRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(produtos);
        }
    }

    @PostMapping("/adicionarItemNoCarrinho")
    public ResponseEntity<Object> adicionarItemNoCarrinho(@RequestBody Carrinho carrinho) {
        
        Carrinho carrinhoExistente = carrinhoRepository.findById(carrinho.getId()).orElse(null);

        if (carrinhoExistente == null) {
            carrinhoRepository.save(carrinho);
            return ResponseEntity.status(HttpStatus.CREATED).body(carrinho);
        } else {
            carrinhoExistente.getItens().addAll(carrinho.getItens());
            carrinhoRepository.save(carrinhoExistente);
            return ResponseEntity.status(HttpStatus.OK).body(carrinhoExistente);
        }
    }
}
