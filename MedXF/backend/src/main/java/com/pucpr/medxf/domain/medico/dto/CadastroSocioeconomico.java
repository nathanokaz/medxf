package com.pucpr.medxf.domain.medico.dto;

public record CadastroSocioeconomico(

        Integer pacienteId,
        String nomeResponsavel,
        Integer quantidadeMoradores,
        String renda,
        String internet,
        String beneficio,
        String planoSaude,
        String moradia

) {
}