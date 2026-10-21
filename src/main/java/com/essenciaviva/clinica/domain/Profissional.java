package com.essenciaviva.clinica.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profissional")
@Getter @Setter
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "registro_profissional", nullable = false, unique = true, length = 50)
    private String registroProfissional;

    @Column(nullable = false, length = 100)
    private String especialidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ATIVO;

    public enum Status { ATIVO, INATIVO }

    // Getters e Setters explícitos para a IDE reconhecer
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getRegistroProfissional() { return registroProfissional; }
    public void setRegistroProfissional(String registroProfissional) { this.registroProfissional = registroProfissional; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}