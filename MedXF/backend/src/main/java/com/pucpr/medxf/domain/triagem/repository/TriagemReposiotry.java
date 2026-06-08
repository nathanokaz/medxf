package com.pucpr.medxf.domain.triagem.repository;

import com.pucpr.medxf.domain.triagem.Triagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TriagemReposiotry extends JpaRepository<Triagem, Integer> {

    List<Triagem> findAllByMedicoId(Integer medico);

    List<Triagem> findAllByPacienteId(Integer id);

}