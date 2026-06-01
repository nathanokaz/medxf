package com.pucpr.medxf.domain.admin.service;

import com.pucpr.medxf.domain.admin.dto.ListaMedicos;
import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.admin.dto.CadastroMedico;
import com.pucpr.medxf.domain.medico.repository.MedicoRepository;
import com.pucpr.medxf.domain.user.User;
import com.pucpr.medxf.domain.user.dto.UserRoles;
import com.pucpr.medxf.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MedicoRepository medicoRepository;
    private final UserRepository userRepository;

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
                .build();
        medicoRepository.save(medico);
    }

    public List<ListaMedicos> listarMedicos() {
        var medicos = medicoRepository.findAll();
        return medicos.stream().map(medico -> new ListaMedicos(medico)).toList();
    }

}
