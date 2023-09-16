package com.sistema_repositorio.sistema_supermercado.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.model.Funcionarios;
import com.sistema_repositorio.sistema_supermercado.repository.funcionarioRepository;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGenerator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
@Validated
public class FuncionarioController {

    @Autowired
    private funcionarioRepository funcionarioRepository;

    public FuncionarioController(funcionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }

    @GetMapping
    public ResponseEntity<Object> listarFuncionarios() {
        long count = funcionarioRepository.count();
        if (count == 0){
            ErroResponse erroResponse = new ErroResponse("Não tem nenhum funcionário cadastrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
        }else{
           return ResponseEntity.status(HttpStatus.OK).body(this.funcionarioRepository.findAll());
        }
    }


    @PostMapping
    public ResponseEntity<Object> adicionarFuncionario(@Valid @RequestBody Funcionarios f) {
        f.setId(sequenceGenerator.generateSequence(Funcionarios.SEQUENCE_NAME));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.funcionarioRepository.save(f));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarFuncionario(@PathVariable long id) {
        Optional<Funcionarios> p = this.funcionarioRepository.findById(id);

        if (p.isPresent()) {
            this.funcionarioRepository.delete(p.get());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Funcionário " + p.get().getNome() + "" + " excluido com sucesso.");
        }
        ErroResponse erroResponse = new ErroResponse("Funcionario não encontrado na base de dados");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarFuncionarioPorId(@PathVariable long id) {
        Optional<Funcionarios> funcionarioEncontrado = this.funcionarioRepository.findById(id);
        ErroResponse erroResponse = new ErroResponse("Funcionário não encontrado");
        if (!funcionarioEncontrado.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(funcionarioEncontrado);
        }
        
    }
}
