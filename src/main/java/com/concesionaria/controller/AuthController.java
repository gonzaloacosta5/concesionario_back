// src/main/java/com/concesionaria/controller/AuthController.java
package com.concesionaria.controller;

import com.concesionaria.model.Usuario;
import com.concesionaria.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService svc;

    public AuthController(UsuarioService svc) {
        this.svc = svc;
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usr) {
        Usuario creado = svc.register(usr);
        creado.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario cred) {
        Usuario auth = svc.authenticate(cred);
        auth.setPassword(null);
        return ResponseEntity.ok(auth);
    }
}
