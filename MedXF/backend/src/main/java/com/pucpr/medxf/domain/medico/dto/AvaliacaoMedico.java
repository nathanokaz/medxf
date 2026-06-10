package com.pucpr.medxf.domain.medico.dto;

import java.util.List;

public record AvaliacaoMedico(

        Integer pacienteId,
        List<Respostas> respostas

) {
}
