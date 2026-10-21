package com.essenciaviva.clinica.controller;

import com.essenciaviva.clinica.domain.Agendamento;
import com.essenciaviva.clinica.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    // POST /api/agendamentos - Cria agendamento validando choque de agenda
    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody Agendamento agendamento) {
        try {
            Agendamento novoAgendamento = agendamentoService.criarAgendamento(agendamento);
            return ResponseEntity.ok(novoAgendamento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno ao processar agendamento.");
        }
    }
}