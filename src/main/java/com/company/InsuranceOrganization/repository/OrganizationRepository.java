package com.company.InsuranceOrganization.repository;

import com.company.InsuranceOrganization.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization findOrganizationByAcceptanceId(Long id);

    void deleteByAcceptanceId(Long id);

    Boolean existsOrganizationByAcceptanceId(Long id);
}
