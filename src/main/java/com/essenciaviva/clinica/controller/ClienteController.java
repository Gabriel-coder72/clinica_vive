package com.essenciaviva.clinica.controller;

import com.essenciaviva.clinica.domain.Cliente;
import com.essenciaviva.clinica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // POST /api/clientes - Cadastra cliente com dados sensíveis criptografados
    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@RequestParam String nome,
                                              @RequestParam String cpf,
                                              @RequestParam String nascimento,
                                              @RequestParam String telefone,
                                              @RequestParam String email,
                                              @RequestParam String endereco,
                                              @RequestParam(required = false) String observacoes) {
        try {
            Cliente clienteSalvo = clienteService.salvarCliente(nome, cpf, nascimento, telefone, email, endereco, observacoes);
            return ResponseEntity.ok(clienteSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // DELETE /api/clientes/{id} - Trata remoção lógica ou física
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerCliente(@PathVariable Long id) {
        try {
            clienteService.deletarOuInativarCliente(id);
            return ResponseEntity.ok("Operação realizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}