package com.sistema_repositorio.sistema_supermercado.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean  
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/carrinho").authenticated().anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("paulo").password("$2a$10$cZyg4RdSISw5tzAHtt/Ru.QWQcKFIQPYU76z9KxUBtHrBQ4GarGMS").roles("ADMIN")
                .and()
                .withUser("user").password("$2a$10$cZyg4RdSISw5tzAHtt/Ru.QWQcKFIQPYU76z9KxUBtHrBQ4GarGMS").roles("USER");
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            
                if ("admin".equals(username)) {
                    return User.withUsername("admin")
                            .password("$2a$10$cZyg4RdSISw5tzAHtt/Ru.QWQcKFIQPYU76z9KxUBtHrBQ4GarGMS")
                            .roles("ADMIN")
                            .build();
                } else if ("user".equals(username)) {
                    return User.withUsername("user")
                            .password("password")
                            .roles("USER")
                            .build();
                } else {
                    throw new UsernameNotFoundException("Usuário não encontrado: " + username);
                }
            }
        };
    }
}
