package com.pucpr.medxf.domain.respostas;

import com.pucpr.medxf.domain.pergunta.Pergunta;
import com.pucpr.medxf.domain.triagem.Triagem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "respostas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private boolean resposta;

    @Column
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    @ManyToOne
    @JoinColumn(name = "triagem_id")
    private Triagem triagem;

}
