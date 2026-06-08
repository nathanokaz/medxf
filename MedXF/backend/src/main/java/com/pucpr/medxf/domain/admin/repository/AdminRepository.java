package com.pucpr.medxf.domain.admin.repository;

import com.pucpr.medxf.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByUser_Email(String email);

    Optional<Admin> findByUserId(Integer user);

}
