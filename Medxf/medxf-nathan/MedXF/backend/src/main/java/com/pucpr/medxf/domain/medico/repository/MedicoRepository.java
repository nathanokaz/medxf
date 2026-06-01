package com.pucpr.medxf.domain.medico.repository;

import com.pucpr.medxf.domain.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    boolean existsByCrm(String crm);

    boolean existsByCpf(String cpf);

}
