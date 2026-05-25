package com.pucpr.medxf.domain.paciente.service;

import com.pucpr.medxf.domain.medico.dto.CadastroPaciente;
import com.pucpr.medxf.domain.medico.dto.ListaPaciente;
import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.paciente.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Transactional
    public void cadastrarPaciente(CadastroPaciente cadastroPaciente) {
        if (pacienteRepository.existsByEmail(cadastroPaciente.email())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        Paciente paciente = Paciente.builder()
                .nome(cadastroPaciente.nome().trim())
                .nomeResponsavel(cadastroPaciente.nomeResponsavel().trim())
                .nascimento(cadastroPaciente.nascimento())
                .sexo(cadastroPaciente.sexo())
                .email(cadastroPaciente.email().trim().toLowerCase())
                .telefone(cadastroPaciente.telefone().trim())
                .historico1(cadastroPaciente.historico1())
                .historico2(cadastroPaciente.historico2())
                .build();
        pacienteRepository.save(paciente);
    }

    public List<ListaPaciente> listarPacientes() {
        var pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(ListaPaciente::new)
                .toList();
    }

}