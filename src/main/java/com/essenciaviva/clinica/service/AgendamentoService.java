package com.essenciaviva.clinica.service;

import com.essenciaviva.clinica.domain.Agendamento;
import com.essenciaviva.clinica.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Agendamento criarAgendamento(Agendamento agendamento) {
        // Regra de negócio: Impede que dois atendimentos sobreponham horários para o mesmo profissional
        long conflitos = agendamentoRepository.countConflitosHorario(
                agendamento.getProfissional().getId(),
                agendamento.getDataAtendimento(),
                agendamento.getHoraInicio(),
                agendamento.getHoraFim()
        );

        if (conflitos > 0) {
            throw new IllegalArgumentException("O profissional selecionado já possui um agendamento neste horário.");
        }

        return agendamentoRepository.save(agendamento);
    }
}