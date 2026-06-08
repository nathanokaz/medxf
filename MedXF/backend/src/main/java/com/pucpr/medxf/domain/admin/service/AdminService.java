package com.pucpr.medxf.domain.admin.service;

import com.pucpr.medxf.domain.admin.Admin;
import com.pucpr.medxf.domain.admin.dto.CadastroMedico;
import com.pucpr.medxf.domain.admin.dto.InformacoesPerfilAdmin;
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
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        var senhaCriptografada =
                new BCryptPasswordEncoder().encode(cadastroMedico.senha());
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
                .user(user)
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

    public List<ListaPaciente> listarPacientes() {
        var pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(ListaPaciente::new).toList();
    }

    public List<String> pegarDadosAdmin() {
        var admin = pegarAdmin().getId();
        var user = pegarUser().getId();
        List<String> informacoes = new ArrayList<>();
        var infosUser = userRepository.findById(user)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));
        var infosAdmin = adminRepository.findByUserId(admin)
                .orElseThrow(() -> new RuntimeException("User não encontrado"));
        informacoes.add(infosAdmin.getNome());
        informacoes.add(infosUser.getEmail());
        return informacoes;
    }

    @Transactional
    public void editarDadosAdmin(InformacoesPerfilAdmin informacoesPerfilAdmin) {
        var admin = pegarAdmin();
        var user = pegarUser();
        if (informacoesPerfilAdmin.nome() != null
                && !informacoesPerfilAdmin.nome().isBlank()) {
            admin.setNome(informacoesPerfilAdmin.nome());
        }
        if (informacoesPerfilAdmin.email() != null
                && !informacoesPerfilAdmin.email().isBlank()) {
            if (userRepository.existsByEmail(informacoesPerfilAdmin.email())
                    && !user.getEmail().equals(informacoesPerfilAdmin.email())) {
                throw new RuntimeException("Email já cadastrado");
            }
            user.setEmail(informacoesPerfilAdmin.email());
        }
        if (informacoesPerfilAdmin.senha() != null
                && !informacoesPerfilAdmin.senha().isBlank()) {
            var senhaCriptografada = new BCryptPasswordEncoder().encode(informacoesPerfilAdmin.senha());
            user.setSenha(senhaCriptografada);
        }
    }

    public List<String> informacoesMedico(Integer id) {
        var medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        List<String> infosMedico = new ArrayList<>();
        infosMedico.add(medico.getNome());
        infosMedico.add(medico.getUser().getEmail());
        infosMedico.add(medico.getCrm());
        infosMedico.add(medico.getHospital());
        infosMedico.add(medico.getCidade());
        infosMedico.add(medico.getEstado());
        infosMedico.add(String.valueOf(id));
        return infosMedico;
    }

    @Transactional
    public void editarMedico(Integer id, CadastroMedico cadastroMedico) {
        var medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        if (cadastroMedico.nome() != null && !cadastroMedico.nome().isBlank()) {
            medico.setNome(cadastroMedico.nome());
        }
        if (cadastroMedico.crm() != null && !cadastroMedico.crm().isBlank()) {
            if (medicoRepository.existsByCrm(cadastroMedico.crm())
                    && !cadastroMedico.crm().equals(medico.getCrm())) {
                throw new RuntimeException("CRM já cadastrado");
            }
            medico.setCrm(cadastroMedico.crm());
        }
        if (cadastroMedico.cpf() != null && !cadastroMedico.cpf().isBlank()) {
            if (medicoRepository.existsByCpf(cadastroMedico.cpf())
                    && !cadastroMedico.cpf().equals(medico.getCpf())) {
                throw new RuntimeException("CPF já cadastrado");
            }
            medico.setCpf(cadastroMedico.cpf());
        }
        if (cadastroMedico.especialidade() != null) {
            medico.setEspecialidade(cadastroMedico.especialidade());
        }
        if (cadastroMedico.hospital() != null && !cadastroMedico.hospital().isBlank()) {
            medico.setHospital(cadastroMedico.hospital());
        }
        if (cadastroMedico.cidade() != null && !cadastroMedico.cidade().isBlank()) {
            medico.setCidade(cadastroMedico.cidade());
        }
        if (cadastroMedico.estado() != null && !cadastroMedico.estado().isBlank()) {
            medico.setEstado(cadastroMedico.estado());
        }
        if (cadastroMedico.email() != null && !cadastroMedico.email().isBlank()) {
            var user = medico.getUser();
            if (userRepository.existsByEmail(cadastroMedico.email())
                    && !cadastroMedico.email().equals(user.getEmail())) {
                throw new RuntimeException("Email já cadastrado");
            }
            user.setEmail(cadastroMedico.email());
        }
        if (cadastroMedico.senha() != null && !cadastroMedico.senha().isBlank()) {
            var senhaCriptografada =
                    new BCryptPasswordEncoder().encode(cadastroMedico.senha());
            medico.getUser().setSenha(senhaCriptografada);
        }
    }

    private Admin pegarAdmin() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminRepository.findByUser_Email(email).orElseThrow(() -> new RuntimeException("Admin não encontrado"));
    }

    private User pegarUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User não encontrado"));
    }

}