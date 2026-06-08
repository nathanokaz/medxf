package com.pucpr.medxf.domain.socioeconomico;

import com.pucpr.medxf.domain.paciente.Paciente;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Socioeconomico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private String nomeResponsavel;
    private Integer quantidadeMoradores;
    private String renda;
    private String internet;
    private String beneficio;
    private String planoSaude;
    private String moradia;
}