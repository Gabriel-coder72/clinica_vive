package com.essenciaviva.clinica.controller;

import com.essenciaviva.clinica.domain.Usuario;
import com.essenciaviva.clinica.dto.LoginRequest;
import com.essenciaviva.clinica.dto.UsuarioResponse;
import com.essenciaviva.clinica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // POST /api/usuarios/cadastrar
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.ok(new UsuarioResponse(novoUsuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST /api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuarioAutenticado = usuarioService.autenticar(
                    loginRequest.getEmail(),
                    loginRequest.getSenha()
            );
            return ResponseEntity.ok(new UsuarioResponse(usuarioAutenticado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}