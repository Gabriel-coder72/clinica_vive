package com.essenciaviva.clinica.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter @Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "cpf_hash", nullable = false, unique = true, length = 64)
    private String cpfHash;

    @Column(name = "cpf_cifrado", nullable = false)
    private byte[] cpfCifrado;

    @Column(name = "nascimento_cif", nullable = false)
    private byte[] nascimentoCifrado;

    @Column(name = "telefone_cif", nullable = false)
    private byte[] telefoneCifrado;

    @Column(name = "email_cif", nullable = false)
    private byte[] emailCifrado;

    @Column(name = "endereco_cif", nullable = false)
    private byte[] enderecoCifrado;

    @Column(name = "observacoes_cif")
    private byte[] observacoesCifradas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ATIVO;

    public enum Status { ATIVO, INATIVO }

    // Getters e Setters explícitos para o editor reconhecer os métodos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpfHash() { return cpfHash; }
    public void setCpfHash(String cpfHash) { this.cpfHash = cpfHash; }

    public byte[] getCpfCifrado() { return cpfCifrado; }
    public void setCpfCifrado(byte[] cpfCifrado) { this.cpfCifrado = cpfCifrado; }

    public byte[] getNascimentoCifrado() { return nascimentoCifrado; }
    public void setNascimentoCifrado(byte[] nascimentoCifrado) { this.nascimentoCifrado = nascimentoCifrado; }

    public byte[] getTelefoneCifrado() { return telefoneCifrado; }
    public void setTelefoneCifrado(byte[] telefoneCifrado) { this.telefoneCifrado = telefoneCifrado; }

    public byte[] getEmailCifrado() { return emailCifrado; }
    public void setEmailCifrado(byte[] emailCifrado) { this.emailCifrado = emailCifrado; }

    public byte[] getEnderecoCifrado() { return enderecoCifrado; }
    public void setEnderecoCifrado(byte[] enderecoCifrado) { this.enderecoCifrado = enderecoCifrado; }

    public byte[] getObservacoesCifradas() { return observacoesCifradas; }
    public void setObservacoesCifradas(byte[] observacoesCifradas) { this.observacoesCifradas = observacoesCifradas; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}