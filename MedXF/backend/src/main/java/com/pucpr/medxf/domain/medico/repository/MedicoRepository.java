package com.pucpr.medxf.domain.medico.repository;

import com.pucpr.medxf.domain.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    boolean existsByCrm(String crm);

    boolean existsByCpf(String cpf);

    Optional<Medico> findByUser_Email(String email);

    List<Medico> findAllByAdminId(Integer admin);

}
