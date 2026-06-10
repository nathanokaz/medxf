package com.pucpr.medxf.domain.pergunta;

import com.pucpr.medxf.domain.respostas.Resposta;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "perguntas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String descricao;

    @OneToMany(mappedBy = "pergunta")
    private List<Resposta> respostas;

}
