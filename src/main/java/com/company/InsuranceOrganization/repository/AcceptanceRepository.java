package com.company.InsuranceOrganization.repository;

import com.company.InsuranceOrganization.models.Acceptance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcceptanceRepository extends JpaRepository<Acceptance, Long> {

    List<Acceptance> findByUserUsername(String username);

}
