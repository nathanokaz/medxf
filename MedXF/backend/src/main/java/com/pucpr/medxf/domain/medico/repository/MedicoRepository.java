package com.pucpr.medxf.domain.medico.repository;

import com.pucpr.medxf.domain.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    boolean existsByEmail(String email);

    boolean existsByCrm(String crm);

}
