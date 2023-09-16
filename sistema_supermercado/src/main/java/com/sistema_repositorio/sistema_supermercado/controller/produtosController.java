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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.model.ResponseOk;
import com.sistema_repositorio.sistema_supermercado.model.produto;
import com.sistema_repositorio.sistema_supermercado.repository.produtoRepository;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGenerator;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@Validated
public class ProdutosController {
    
    @Autowired
    private produtoRepository produtoRepository;

    public ProdutosController (produtoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public ResponseEntity<Object> listarProdutos() {
        long count = produtoRepository.count();
        if (count == 0){
            ErroResponse erroResponse = new ErroResponse("Não tem nenhum produto cadastrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(this.produtoRepository.findAll());
        }
    }
    @PostMapping
    public ResponseEntity<Object> adicionarProduto(@Valid @RequestBody produto p) {
        p.setId(sequenceGenerator.generateSequence(produto.SEQUENCE_NAME));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.produtoRepository.save(p));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarProduto(@PathVariable long id) {
        Optional<produto> p = this.produtoRepository.findById(id);
        ResponseOk responseOk = new ResponseOk("Produto " + p.get().getNome() +""+ " excluido com sucesso.");
        if (p.isPresent()) {
            this.produtoRepository.delete(p.get());
            return ResponseEntity.status(HttpStatus.OK).body(responseOk);
        }
        ErroResponse erroResponse = new ErroResponse("Produto não encontrado na base de dados");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarProdutoPorId(@PathVariable long id){
        Optional <produto> produtoEncontrado = this.produtoRepository.findById(id);
        ErroResponse erroResponse = new ErroResponse("Produto não encontrado");
        if (!produtoEncontrado.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoEncontrado);
    }
     @GetMapping("/nome")
    public ResponseEntity<Object> listarProdutoPorNome(@RequestParam(value = "searchNome", required = true) String nome) {
        List<produto> produtosEncontrados = new ArrayList<>();
        for(produto p : this.produtoRepository.findAll()){
            if(containsIgnoreCase(p.getNome(), nome)){
                produtosEncontrados.add(p);
            }
        }
        
        if (produtosEncontrados.isEmpty()) {
            ErroResponse erroResponse = new ErroResponse("Produto não encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(produtosEncontrados);
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