package com.pucpr.medxf.domain.Socioeconomico;

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

    @Column(name = "nome_responsavel", nullable = false)
    private String nomeResponsavel;

    @Column(name = "quantidade_moradores", nullable = false)
    private Integer quantidadeMoradores;

    @Column(nullable = false)
    private String renda;

    @Column(nullable = false)
    private String internet;

    @Column(nullable = false)
    private String beneficio;

    @Column(name = "plano_saude", nullable = false)
    private String planoSaude;

    @Column(nullable = false)
    private String moradia;

}