package com.pucpr.medxf.domain.medico.dto;

import java.time.LocalDate;

public record ListaPacientesHome(

        Integer id,
        String nome,
        String email,
        int idade,
        int sintomas,
        Risco risco,
        LocalDate data,
        Status status

) {
}
