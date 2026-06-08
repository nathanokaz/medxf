package com.pucpr.medxf.domain.pergunta.repository;


import com.pucpr.medxf.domain.pergunta.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
}
