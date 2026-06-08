package com.pucpr.medxf.domain.medico.dto;

import lombok.Data;

@Data
public class CadastroSocioeconomico {

    private Integer pacienteId;
    private String nomeResponsavel;
    private Integer quantidadeMoradores;
    private String renda;
    private String internet;
    private String beneficio;
    private String planoSaude;
    private String moradia;
}