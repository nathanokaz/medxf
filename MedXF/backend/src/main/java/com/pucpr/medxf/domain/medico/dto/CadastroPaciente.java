package com.pucpr.medxf.domain.medico.dto;

import com.pucpr.medxf.domain.paciente.dto.Sexo;
import com.pucpr.medxf.domain.paciente.dto.Historico;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CadastroPaciente(

        @NotBlank(message = "Nome obrigatório.")
        String nome,

        @NotBlank(message = "Nome do Responsável obrigatório.")
        String nomeResponsavel,

        @NotNull(message = "Data de Nascimento obrigatória.")
        LocalDate nascimento,

        @NotNull(message = "Sexo obrigatório.")
        Sexo sexo,

        @NotBlank(message = "Email obrigatório.")
        @Email(message = "Email inválido.")
        String email,

        @NotBlank(message = "Telefone obrigatório.")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
                message = "Telefone inválido."
        )
        String telefone,

        @NotNull(message = "Histórico deficiência intelectual obrigatório.")
        Historico historico1,

        @NotNull(message = "Histórico X frágil obrigatório.")
        Historico historico2

) {
}