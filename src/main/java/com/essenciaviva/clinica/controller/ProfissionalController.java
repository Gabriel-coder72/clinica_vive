package com.essenciaviva.clinica.controller;

import com.essenciaviva.clinica.domain.Profissional;
import com.essenciaviva.clinica.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    // GET /api/profissionais - Lista todos os profissionais registrados
    @GetMapping
    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    // POST /api/profissionais - Cadastra um novo profissional
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Profissional profissional) {
        try {
            Profissional novoProfissional = profissionalRepository.save(profissional);
            return ResponseEntity.ok(novoProfissional);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar profissional: " + e.getMessage());
        }
    }
}