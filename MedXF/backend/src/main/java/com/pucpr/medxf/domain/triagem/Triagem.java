package com.pucpr.medxf.domain.triagem;

import com.pucpr.medxf.domain.medico.Medico;
import com.pucpr.medxf.domain.paciente.Paciente;
import com.pucpr.medxf.domain.respostas.Resposta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "triagem")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Triagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @Column(nullable = false)
    private LocalDateTime criado_em;

    @OneToMany(mappedBy = "triagem")
    private List<Resposta> respostas;

}
