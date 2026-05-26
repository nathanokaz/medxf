package com.pucpr.medxf.domain.medico.service;

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
public class MedicoService {

    private final PacienteRepository pacienteRepository;

    @Transactional
    public void cadastrarPaciente(CadastroPaciente cadastroPaciente) {
        if (pacienteRepository.existsByEmail(cadastroPaciente.email())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        if (pacienteRepository.existsByTelefone(cadastroPaciente.telefone())) {
            throw new IllegalArgumentException("Telefone já cadastrado.");
        }
        if (pacienteRepository.existsByCpf(cadastroPaciente.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        Paciente paciente = Paciente.builder()
                .nome(cadastroPaciente.nome().trim())
                .nascimento(cadastroPaciente.nascimento())
                .sexo(cadastroPaciente.sexo())
                .email(cadastroPaciente.email().trim().toLowerCase())
                .telefone(cadastroPaciente.telefone().trim())
                .historico1(cadastroPaciente.historico1())
                .historico2(cadastroPaciente.historico2())
                .cpf(cadastroPaciente.cpf())
                .build();
        pacienteRepository.save(paciente);
    }

    public List<ListaPaciente> listarPacientes() {
        var pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(ListaPaciente::new).toList();
    }

}
