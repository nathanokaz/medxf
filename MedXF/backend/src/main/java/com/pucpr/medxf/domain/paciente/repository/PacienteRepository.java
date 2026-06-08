package com.pucpr.medxf.domain.paciente.repository;

import com.pucpr.medxf.domain.paciente.Paciente;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    boolean existsByCpf(String cpf);

    List<Paciente> findAllByMedicoId(Integer medico);

}