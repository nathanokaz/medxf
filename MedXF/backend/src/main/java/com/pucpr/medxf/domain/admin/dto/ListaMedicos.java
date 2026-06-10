package com.pucpr.medxf.domain.admin.dto;

import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.medico.dto.Especialidade;

public record ListaMedicos(

        Integer id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade,
        String hospital,
        String cidade,
        String estado,
        String fotoPerfil) {

    public ListaMedicos(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getUser().getEmail(), medico.getCrm(), medico.getEspecialidade(),
                medico.getHospital(), medico.getCidade(), medico.getEstado(), medico.getFotoPerfil());
    }

}
