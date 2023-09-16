package com.sistema_repositorio.sistema_supermercado.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
public class Usuarios implements UserDetails {

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
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

    @Override
    public boolean isAccountNonExpired() {
        // Implemente a lógica de verificação de conta não expirada (por padrão, retorne true)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Implemente a lógica de verificação de conta não bloqueada (por padrão, retorne true)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implemente a lógica de verificação de credenciais não expiradas (por padrão, retorne true)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Implemente a lógica de verificação de conta ativada (por padrão, retorne true)
        return true;
    }
}