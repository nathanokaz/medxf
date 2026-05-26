package com.pucpr.medxf.domain.paciente;

import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.paciente.dto.Historico;
import com.pucpr.medxf.domain.paciente.dto.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate nascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Historico historico1;

    @Enumerated(EnumType.STRING)
    private Historico historico2;

    @Column(nullable = false, unique = true)
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;


}