package com.pucpr.medxf.domain.admin.dto;

import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.medico.dto.Especialidade;

public record ListaMedicos(

        String nome,
        String email,
        String crm,
        Especialidade especialidade,
        String hospital,
        String cidade,
        String estado) {

    public ListaMedicos(Medico medico) {
        this(medico.getNome(), medico.getUser().getEmail(), medico.getCrm(), medico.getEspecialidade(),
                medico.getHospital(), medico.getCidade(), medico.getEstado());
    }

}
