package com.sistema_repositorio.sistema_supermercado.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.model.Funcionarios;
import com.sistema_repositorio.sistema_supermercado.model.Usuarios;
import com.sistema_repositorio.sistema_supermercado.repository.usuariosRepository;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGenerator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teste")
public class UsuariosController {
    
    @Autowired
    private final usuariosRepository usuariosRepository;
    
    public UsuariosController (usuariosRepository usuariosRepository){
        this.usuariosRepository = usuariosRepository;
    }
    @PostMapping
    public ResponseEntity<Usuarios> criarUsuario(@Valid @RequestBody Usuarios usuario) {
        usuario.setId(sequenceGenerator.generateSequence(Usuarios.SEQUENCE_NAME));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.usuariosRepository.save(usuario));
    }
    @GetMapping
    public ResponseEntity<Object> listarFuncionarios() {
        long count = usuariosRepository.count();
        if (count == 0){
            ErroResponse erroResponse = new ErroResponse("Não tem nenhum funcionário cadastrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
        }else{
           return ResponseEntity.status(HttpStatus.OK).body(this.usuariosRepository.findAll());
        }
    }
}
