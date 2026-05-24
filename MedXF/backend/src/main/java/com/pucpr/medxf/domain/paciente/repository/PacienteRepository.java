package com.pucpr.medxf.domain.paciente.repository;

import com.pucpr.medxf.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    boolean existsByEmail(String email);

}