package com.sistema_repositorio.sistema_supermercado.model;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

@Entity
@EnableWebSecurity
public class Usuarios implements UserDetails {

    @Transient
    public static final String SEQUENCE_NAME = "usuarios_sequence";
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Implemente a lógica de verificação de conta não expirada (por padrão, retorne
        // true)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Implemente a lógica de verificação de conta não bloqueada (por padrão,
        // retorne true)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implemente a lógica de verificação de credenciais não expiradas (por padrão,
        // retorne true)
        return true;
    }

    @Override
    public boolean isEnabled() {
    LocalDate ultimaDataLogin = LocalDate.now();
    try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
        MongoDatabase database = mongoClient.getDatabase("gerenciadorSupermercado");
        MongoCollection<Document> collection = database.getCollection("cliente");

        Document query = collection.find().first();
        if (query != null) {
            Object campo = query.get("ultimoLogin");
            ultimaDataLogin = (LocalDate) campo;
        }
    }
    if (ultimaDataLogin != null) {
        LocalDate dataAtual = LocalDate.now();
        long anosDesdeUltimoLogin = ChronoUnit.YEARS.between(ultimaDataLogin, dataAtual);
        if (anosDesdeUltimoLogin <= 5) {
            return true; 
        }
    }

    return false;
    }
}
