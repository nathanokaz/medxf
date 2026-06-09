package com.pucpr.medxf.domain.paciente.service;

import com.pucpr.medxf.domain.paciente.repository.PacienteRepository;
import com.pucpr.medxf.domain.triagem.Triagem;
import com.pucpr.medxf.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public List<String> pegarInfosPaciente() {
        var paciente = pegarPaciente();
        List<String> lista = new ArrayList<>();
        lista.add(paciente.getPaciente().getNome());
        return lista;
    }

    public List<Triagem> pegarTriagem() {
        var paciente = pegarPaciente().getPaciente();
        return paciente.getTriagens();
    }

    public User pegarPaciente() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return pacienteRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User não encontrado")).getUser();
    }

}
