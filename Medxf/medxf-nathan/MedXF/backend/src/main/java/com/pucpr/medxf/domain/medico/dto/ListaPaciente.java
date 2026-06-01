package com.pucpr.medxf.domain.medico.dto;

import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.paciente.dto.Historico;
import com.pucpr.medxf.domain.paciente.dto.Sexo;

import java.time.LocalDate;

public record ListaPaciente(

        String nome,
        String cpf,
        LocalDate nascimento,
        Sexo sexo,
        String email,
        String telefone,
        Historico historico1,
        Historico historico2

) {

    public ListaPaciente(Paciente paciente) {
        this(paciente.getNome(), paciente.getCpf(), paciente.getNascimento(), paciente.getSexo(),
                paciente.getEmail(), paciente.getTelefone(), paciente.getHistorico1(), paciente.getHistorico2()
        );
    }

}