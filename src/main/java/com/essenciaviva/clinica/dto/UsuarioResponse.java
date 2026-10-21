package com.essenciaviva.clinica.dto;

import com.essenciaviva.clinica.domain.Usuario;

public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private String perfil;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil() != null ? usuario.getPerfil().name() : null;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPerfil() { return perfil; }
}