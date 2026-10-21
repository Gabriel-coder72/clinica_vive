package com.essenciaviva.clinica.service;

import com.essenciaviva.clinica.domain.Usuario;
import com.essenciaviva.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Regista um novo utilizador
    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um utilizador registado com este e-mail.");
        }
        return usuarioRepository.save(usuario);
    }

    // Autentica o utilizador
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Utilizador não encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Palavra-passe incorreta.");
        }

        return usuario;
    }
}