package com.sistema_repositorio.sistema_supermercado.Conexões;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CriptografiaSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCodificada = encoder.encode("gestor");
        System.out.println("Senha codificada: " + senhaCodificada);
    }

}