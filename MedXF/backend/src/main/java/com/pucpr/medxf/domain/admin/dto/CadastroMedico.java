package com.pucpr.medxf.domain.admin.dto;

import com.pucpr.medxf.domain.medico.dto.Especialidade;
import jakarta.validation.constraints.*;

public record CadastroMedico(

        @NotBlank(message = "Nome obrigatório.")
        String nome,

        @NotBlank(message = "Email obrigatório.")
        @Email(message = "Email inválido.")
        String email,

        @NotBlank(message = "Senha obrigatória.")
        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres.")
        String senha,

        @NotBlank(message = "CRM obrigatório.")
        @Size(min = 6, max = 6, message = "CRM inválido.")
        String crm,

        @NotNull(message = "Especialidade obrigatória.")
        Especialidade especialidade,

        @NotBlank(message = "Hospital obrigatório.")
        String hospital,

        @NotBlank(message = "Cidade obrigatória.")
        String cidade,

        @NotBlank(message = "Estado obrigatório.")
        String estado,

        @NotBlank(message = "CPF obrigatório.")
        String cpf) {
}
