package com.pucpr.medxf.domain.Socioeconomico.repository;

import com.pucpr.medxf.domain.Socioeconomico.Socioeconomico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocioeconomicoRepository extends JpaRepository<Socioeconomico, Integer> {

    Optional<Socioeconomico> findByPacienteId(Integer pacienteId);

}