package com.pucpr.medxf.domain.medico.dto;

import com.pucpr.medxf.domain.paciente.dto.Historico;
import com.pucpr.medxf.domain.paciente.dto.Sexo;
import com.pucpr.medxf.domain.triagem.Triagem;

import java.time.LocalDate;
import java.util.List;

public record RelatorioPaciente(

        String nome,
        LocalDate nascimento,
        Sexo sexo,
        String email,
        String telefone,
        String cpf,
        Historico historico1,
        Historico historico2,
        String medico,
        List<Triagem> totalTriagens

){
}
