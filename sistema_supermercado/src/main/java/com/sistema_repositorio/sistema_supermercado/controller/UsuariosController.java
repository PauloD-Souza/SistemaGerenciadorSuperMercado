package com.sistema_repositorio.sistema_supermercado.controller;

import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.model.Funcionarios;
import com.sistema_repositorio.sistema_supermercado.model.Usuarios;
import com.sistema_repositorio.sistema_supermercado.repository.usuariosRepository;
import com.sistema_repositorio.sistema_supermercado.sequenceMongodb.sequenceGenerator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teste")
public class UsuariosController {

    private final usuariosRepository usuariosRepository;

    public UsuariosController(usuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @PostMapping
    public ResponseEntity<Object> criarUsuario(@Valid @RequestBody Usuarios usuario) {
        // Verifique se o nome de usuário já existe no banco

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("gerenciadorSupermercado");
            MongoCollection<Document> collection = database.getCollection("usuarios");

            Document query = collection.find().first(); 
            if (query != null) {
                Object campo = query.get("username");
                if (usuario.getUsername().equals(campo)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario Já cadastrado");
                } else {
                    usuario.setId(sequenceGenerator.generateSequence(Usuarios.SEQUENCE_NAME));
                    Usuarios createdUser = usuariosRepository.save(usuario);
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
                }
            }else if (query == null){
                    usuario.setId(sequenceGenerator.generateSequence(Usuarios.SEQUENCE_NAME));
                    Usuarios createdUser = usuariosRepository.save(usuario);
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
            }else {
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }
}
