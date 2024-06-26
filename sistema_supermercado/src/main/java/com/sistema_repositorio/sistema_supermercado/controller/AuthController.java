package com.sistema_repositorio.sistema_supermercado.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema_repositorio.sistema_supermercado.model.ErroResponse;
import com.sistema_repositorio.sistema_supermercado.model.ResponseOk;
import com.sistema_repositorio.sistema_supermercado.model.Usuarios;
import com.sistema_repositorio.sistema_supermercado.repository.usuariosRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private usuariosRepository usuariosRepository;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> loginData) {
    String username = loginData.get("username");
    String password = loginData.get("password");
        Optional<Usuarios> usuarioOptional = usuariosRepository.findByUsername(username);

        if (usuarioOptional.isPresent()) {
            Usuarios usuario = usuarioOptional.get();
            ResponseOk response = new ResponseOk ("Login bem-sucedido");
            ErroResponse erroResponse = new ErroResponse("Senha Incorreta");
            
           
            if (usuario.getPassword().equals(password)) {
                LocalDate currentDate = LocalDate.now();
                usuario.setLastLoginDate(currentDate);
                usuariosRepository.save(usuario);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erroResponse);
            }
        } else {
            ErroResponse erroResponses = new ErroResponse("Usuario Inexistente");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponses);
        }
    }
        
}
