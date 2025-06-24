// src/main/java/com/concesionaria/service/UsuarioService.java
package com.concesionaria.service;

import com.concesionaria.model.Usuario;
import com.concesionaria.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario register(Usuario nuevo) {
        if (repo.findByUsername(nuevo.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Usuario ya existente");
        }
        nuevo.setPassword(encoder.encode(nuevo.getPassword()));
        return repo.save(nuevo);
    }

    public Usuario authenticate(Usuario cred) {
        Usuario u = repo.findByUsername(cred.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
        if (!encoder.matches(cred.getPassword(), u.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        return u;
    }
}
