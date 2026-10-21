package com.essenciaviva.clinica.repository;

import com.essenciaviva.clinica.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Regra de negócio: Impede que dois atendimentos sobreponham horários para o mesmo profissional
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.profissional.id = :profId " +
            "AND a.dataAtendimento = :data " +
            "AND a.situacao <> 'CANCELADO' " +
            "AND (:inicio < a.horaFim AND :fim > a.horaInicio)")
    long countConflitosHorario(@Param("profId") Long profissionalId,
                               @Param("data") LocalDate data,
                               @Param("inicio") LocalTime horaInicio,
                               @Param("fim") LocalTime horaFim);

    // Regra de negócio: Verifica se o cliente possui agendamentos concluídos antes de permitir exclusão
    boolean existsByClienteIdAndSituacao(Long clienteId, Agendamento.Situacao situacao);
}