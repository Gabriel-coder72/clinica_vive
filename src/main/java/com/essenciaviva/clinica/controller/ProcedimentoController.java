package com.essenciaviva.clinica.controller;

import com.essenciaviva.clinica.domain.Procedimento;
import com.essenciaviva.clinica.repository.ProcedimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedimentos")
public class ProcedimentoController {

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    // GET /api/procedimentos - Lista todos os procedimentos estéticos
    @GetMapping
    public List<Procedimento> listarTodos() {
        return procedimentoRepository.findAll();
    }

    // POST /api/procedimentos - Cadastra um novo procedimento estetico
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Procedimento procedimento) {
        try {
            Procedimento novoProcedimento = procedimentoRepository.save(procedimento);
            return ResponseEntity.ok(novoProcedimento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar procedimento: " + e.getMessage());
        }
    }
}
