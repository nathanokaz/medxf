package com.pucpr.medxf.domain.respostas.repository;

import com.pucpr.medxf.domain.respostas.Resposta;
import com.pucpr.medxf.domain.triagem.Triagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Integer> {

    List<Resposta> findAllByTriagemIn(List<Triagem> triagens);

}
