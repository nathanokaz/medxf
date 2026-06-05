package com.pucpr.medxf.domain.medico.dto;

import com.pucpr.medxf.domain.paciente.dto.Historico;
import com.pucpr.medxf.domain.paciente.dto.Sexo;

import java.time.LocalDate;

public record InformacoesPaciente(

        String nome,
        String cpf,
        LocalDate nascimento,
        Sexo sexo,
        String telefone,
        Historico historico1,
        Historico historico2

) {
}
