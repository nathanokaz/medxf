package com.pucpr.medxf.domain.medico.service;

import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.medico.dto.AvaliacaoMedico;
import com.pucpr.medxf.domain.medico.dto.CadastroPaciente;
import com.pucpr.medxf.domain.medico.dto.ListaPaciente;
import com.pucpr.medxf.domain.medico.dto.Respostas;
import com.pucpr.medxf.domain.medico.repository.MedicoRepository;
import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.paciente.repository.PacienteRepository;
import com.pucpr.medxf.domain.pergunta.repository.PerguntaRepository;
import com.pucpr.medxf.domain.respostas.Resposta;
import com.pucpr.medxf.domain.respostas.repository.RespostaRepository;
import com.pucpr.medxf.domain.triagem.Triagem;
import com.pucpr.medxf.domain.triagem.repository.TriagemReposiotry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final PerguntaRepository perguntaRepository;
    private final RespostaRepository respostaRepository;
    private final TriagemReposiotry triagemReposiotry;

    @Transactional
    public Paciente cadastrarPaciente(CadastroPaciente cadastroPaciente) {
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
                .medico(pegarMedico())
                .build();
        pacienteRepository.save(paciente);
        return paciente;
    }

    @Transactional
    public void cadastrarAvaliacao(AvaliacaoMedico avaliacaoMedico) {
        Paciente paciente = pacienteRepository.findById(avaliacaoMedico.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Triagem triagem = Triagem.builder()
                .criado_em(LocalDateTime.now())
                .medico(pegarMedico())
                .paciente(paciente)
                .build();
        triagemReposiotry.save(triagem);
        for (Respostas dto : avaliacaoMedico.respostas()) {
            var pergunta = perguntaRepository.findById(dto.pergunta())
                    .orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));
            Resposta resposta = Resposta.builder()
                    .pergunta(pergunta)
                    .resposta(Boolean.TRUE.equals(dto.resposta()))
                    .observacao(dto.observacao())
                    .triagem(triagem)
                    .build();
            respostaRepository.save(resposta);
        }
    }

    public List<ListaPaciente> listarPacientes() {
        var pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(ListaPaciente::new).toList();
    }

    private Medico pegarMedico() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return medicoRepository.findByUser_Email(email).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
    }

}
