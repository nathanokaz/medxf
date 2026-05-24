package com.pucpr.medxf.domain.medico.service;

import com.pucpr.medxf.domain.admin.dto.CadastroMedico;
import com.pucpr.medxf.domain.admin.dto.ListaMedicos;
import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.medico.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;

    @Transactional
    public void cadastrarMedico(CadastroMedico cadastroMedico) {

        if (medicoRepository.existsByEmail(cadastroMedico.email())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        if (medicoRepository.existsByCrm(cadastroMedico.crm())) {
            throw new IllegalArgumentException("CRM já cadastrado.");
        }

        var senhaCriptografada =
                new BCryptPasswordEncoder().encode(cadastroMedico.senha());

        Medico medico = Medico.builder()
                .nome(cadastroMedico.nome().trim())
                .email(cadastroMedico.email().trim().toLowerCase())
                .senha(senhaCriptografada)
                .crm(cadastroMedico.crm().trim())
                .especialidade(cadastroMedico.especialidade())
                .hospital(cadastroMedico.hospital().trim())
                .cidade(cadastroMedico.cidade().trim())
                .estado(cadastroMedico.estado().trim())
                .build();

        medicoRepository.save(medico);
    }

    public List<ListaMedicos> listarMedicos() {

        var medicos = medicoRepository.findAll();

        return medicos.stream().map(ListaMedicos::new).toList();
    }

}