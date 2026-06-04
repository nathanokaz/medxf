package com.pucpr.medxf.domain.medico.dto;

public record InformacoesPerfil(

        String nome,
        String email,
        String senha,
        String crm,
        Especialidade especialidade,
        String hospital,
        String cidade,
        String estado

) {
}
