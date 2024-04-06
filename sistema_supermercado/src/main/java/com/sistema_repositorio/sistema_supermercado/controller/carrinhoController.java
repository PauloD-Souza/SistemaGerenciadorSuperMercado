package com.sistema_repositorio.sistema_supermercado.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sistema_repositorio.sistema_supermercado.model.Carrinho;
import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.model.Produto;
import com.sistema_repositorio.sistema_supermercado.repository.carrinhoRepository;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGenerator;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGeneratorService;

@RestController
@RequestMapping("/carrinho")
public class carrinhoController {

    @Autowired
    private sequenceGeneratorService sequenceGeneratorService;

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
        carrinho.setId(sequenceGenerator.generateSequence(carrinho.SEQUENCE_NAME));
        Carrinho carrinhoExistente = carrinhoRepository.findById(carrinho.getId()).orElse(null);

        if (carrinhoExistente == null) {
            carrinhoRepository.save(carrinho);
            return ResponseEntity.status(HttpStatus.CREATED).body(carrinho);
        } else {
            carrinhoExistente.getProdutos().addAll(carrinho.getProdutos());
            carrinhoRepository.save(carrinhoExistente);
            return ResponseEntity.status(HttpStatus.OK).body(carrinhoExistente);
        }
    }

    @GetMapping("/calcularTotalPagamento")
    public ResponseEntity<Object> calcularTotalPagamento() {
        BigDecimal total = BigDecimal.ZERO;
        List<Carrinho> carrinhos = carrinhoRepository.findAll();

        for (Carrinho carrinho : carrinhos) {
            total = total.add(carrinho.calcularTotal());
        }

        TotalResponse totalResponse = new TotalResponse(total);

        return ResponseEntity.ok().body(totalResponse);
    }
    static class TotalResponse {
        private BigDecimal total;

        public TotalResponse(BigDecimal total) {
            this.total = total;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }
    }
    @DeleteMapping("/carrinho/{id}")
    public ResponseEntity<Object> deletarCarrinho(@PathVariable long id) {
        if (!carrinhoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carrinhoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
