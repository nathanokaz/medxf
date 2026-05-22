package com.pucpr.medxf.domain.medico;

import com.pucpr.medxf.domain.medico.dto.Especialidade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Column(nullable = false)
    private String hospital;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

}
