package com.pucpr.medxf.domain.admin.service;

import com.pucpr.medxf.domain.admin.Admin;
import com.pucpr.medxf.domain.admin.dto.CadastroMedico;
import com.pucpr.medxf.domain.admin.dto.ListaMedicos;
import com.pucpr.medxf.domain.admin.repository.AdminRepository;
import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.medico.dto.ListaPaciente;
import com.pucpr.medxf.domain.medico.repository.MedicoRepository;
import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.paciente.repository.PacienteRepository;
import com.pucpr.medxf.domain.triagem.Triagem;
import com.pucpr.medxf.domain.triagem.repository.TriagemReposiotry;
import com.pucpr.medxf.domain.user.User;
import com.pucpr.medxf.domain.user.dto.UserRoles;
import com.pucpr.medxf.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MedicoRepository medicoRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PacienteRepository pacienteRepository;
    private final TriagemReposiotry triagemReposiotry;

    @Transactional
    public void cadastrarMedico(CadastroMedico cadastroMedico) {
        if (userRepository.existsByEmail(cadastroMedico.email())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        if (medicoRepository.existsByCrm(cadastroMedico.crm())) {
            throw new IllegalArgumentException("CRM já cadastrado.");
        }
        if (medicoRepository.existsByCpf(cadastroMedico.cpf())) {
            throw new IllegalArgumentException("CPF ja cadastrado.");
        }
        var senhaCriptografada = new BCryptPasswordEncoder().encode(cadastroMedico.senha());
        User user = User.builder()
                .email(cadastroMedico.email())
                .senha(senhaCriptografada)
                .role(UserRoles.USER)
                .criadoEm(LocalDateTime.now())
                .build();
        userRepository.save(user);
        Medico medico = Medico.builder()
                .nome(cadastroMedico.nome())
                .crm(cadastroMedico.crm())
                .especialidade(cadastroMedico.especialidade())
                .hospital(cadastroMedico.hospital())
                .cidade(cadastroMedico.cidade())
                .estado(cadastroMedico.estado())
                .cpf(cadastroMedico.cpf())
                .admin(pegarAdmin())
                .build();
        medicoRepository.save(medico);
    }

    public List<ListaMedicos> listarMedicos() {
        var medicos = medicoRepository.findAll();
        return medicos.stream().map(medico -> new ListaMedicos(medico)).toList();
    }

    public List<String> informacoesNumericasHome() {
        int totalMedicos = 0;
        int totalPacientes = 0;
        int totalTriagens = 0;
        var medicos = medicoRepository.findAll();
        var pacientes = pacienteRepository.findAll();
        var triagens = triagemReposiotry.findAll();
        for (Medico m : medicos) {
            totalMedicos += 1;
        }
        for (Paciente p : pacientes) {
            totalPacientes += 1;
        }
        for (Triagem t : triagens) {
            totalTriagens += 1;
        }
        List<String> informacoes = new ArrayList<>();
        informacoes.add(String.valueOf(totalMedicos));
        informacoes.add(String.valueOf(totalPacientes));
        informacoes.add(String.valueOf(totalTriagens));
        return informacoes;
    }

    public List<String> InformacoesAdmin() {
        var admin = pegarAdmin();
        List<String> infos = new ArrayList<>();
        infos.add(admin.getNome());
        return infos;
    }

    private Admin pegarAdmin() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminRepository.findByUser_Email(email).orElseThrow(() -> new RuntimeException("Admin não encontrado"));
    }

    public List<ListaPaciente> listarPacientes() {
        var pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(ListaPaciente::new).toList();
    }

}
